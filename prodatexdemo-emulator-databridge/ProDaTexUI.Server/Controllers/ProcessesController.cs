/*

Copyright (c) 2024 Fraunhofer-Institut f&uuml;r Produktionstechnik und Automatisierung IPA

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

*/
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using Opc.Ua;
using Opc.Ua.Client;
using Opc.Ua.Configuration;
using System.Net.Sockets;
using System.Reflection;

namespace ProDaTexUI.Server.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProcessesController : ControllerBase
    {
        private readonly ILogger<ProcessesController> _logger;
        private readonly UAClient[] uaClient;
        Processes processes;
        Configuration configuration;
        

        public ProcessesController(ILogger<ProcessesController> logger, Processes processes, Configuration config)
        {
            //_logger = logger;
            this.processes = processes;
            this.configuration = config;    
            uaClient = new UAClient[config.uris.Count];

            string myPath = Path.GetDirectoryName(Assembly.GetEntryAssembly().Location);
            ApplicationInstance application = new ApplicationInstance
            {
                ApplicationName = "ProDaTex_UIClient",
                ApplicationType = ApplicationType.Client,
            };

            // load the application configuration.
            ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration()
            {

            };
            applicationConfiguration.ApplicationUri = "urn:ProDaTexDemo";
            applicationConfiguration.ProductUri = "http://demo.prodatexproject.com";
            applicationConfiguration.ApplicationType = ApplicationType.Client;
            applicationConfiguration.ApplicationName = "UIClient";
            //Client configuration
            applicationConfiguration.ClientConfiguration = new ClientConfiguration
            {
                DefaultSessionTimeout = 20 * 1000,
                MinSubscriptionLifetime = 60 * 1000,

            };
            if (applicationConfiguration.SecurityConfiguration == null)
                applicationConfiguration.SecurityConfiguration = new SecurityConfiguration();
            applicationConfiguration.SecurityConfiguration.ApplicationCertificate = new CertificateIdentifier
            {
                StoreType = "Directory",
                StorePath = myPath + "CertificateStores/MachineDefault",
                SubjectName = applicationConfiguration.ApplicationName,
            };
            application.ApplicationConfiguration = applicationConfiguration;
            bool haveAppCertificate = application.CheckApplicationInstanceCertificate(false, minimumKeySize: 0).Result;
            if (!haveAppCertificate)
            {
                throw new Exception("Application instance certificate invalid!");
            }
            
            for (int i = 0; i < config.uris.Keys.Count; i++ )
            {
                uaClient[i] = new UAClient(application.ApplicationConfiguration, null, TextWriter.Null, ClientBase.ValidateResponse)
                {
                    AutoAccept = true,
                    SessionLifeTime = 60_000,
                };
            }
        }

        [HttpGet]
        public Processes Get()
        {
            return processes;

        }
        [Route("start/{qty?}")]
        public string Start(string qty)
        {
            var quitCTS = new CancellationTokenSource();
            int i = 0;
            foreach (var uri in configuration.uris.Keys)
            {
                bool connected = uaClient[i].ConnectAsync(new Uri(uri).ToString(), false, quitCTS.Token).Result;
                if (!connected) return "No connection to " + uri;
                var sub = new Subscription(uaClient[i].Session.DefaultSubscription)
                {
                    DisplayName = "Console ReferenceClient Subscription",
                    PublishingEnabled = true,
                    PublishingInterval = 1000,
                    LifetimeCount = 0,
                    MinLifetimeInterval = 10 * 1000,
                };
                uaClient[i].Session.AddSubscription(sub);
                sub.Create();
                MonitoredItem stringMonitoredItem = new MonitoredItem(sub.DefaultItem);
                // String Node - Objects\CTT\Scalar\Simulation\String
                stringMonitoredItem.StartNodeId = new NodeId("ns=3;i=6012");
                stringMonitoredItem.AttributeId = Attributes.Value;
                stringMonitoredItem.DisplayName = configuration.uris[uri]+"_state";
                stringMonitoredItem.SamplingInterval = 10;
                stringMonitoredItem.QueueSize = 10;
                stringMonitoredItem.Notification += OnMonitoredItemNotification;

                sub.AddItem(stringMonitoredItem);
                MonitoredItem stringMonitoredItem_gutTeile = new MonitoredItem(sub.DefaultItem);
                stringMonitoredItem_gutTeile.StartNodeId = new NodeId("ns=3;i=6203");
                stringMonitoredItem_gutTeile.AttributeId = Attributes.Value;
                stringMonitoredItem_gutTeile.DisplayName = configuration.uris[uri] + "_gutTeile";
                stringMonitoredItem_gutTeile.SamplingInterval = 10;
                stringMonitoredItem_gutTeile.QueueSize = 10;
                stringMonitoredItem_gutTeile.Notification += OnMonitoredItemNotification;

                sub.AddItem(stringMonitoredItem_gutTeile);
                MonitoredItem stringMonitoredItem_schlechtTeile = new MonitoredItem(sub.DefaultItem);
                stringMonitoredItem_schlechtTeile.StartNodeId = new NodeId("ns=3;i=6202");
                stringMonitoredItem_schlechtTeile.AttributeId = Attributes.Value;
                stringMonitoredItem_schlechtTeile.DisplayName = configuration.uris[uri] + "_schlechtTeile";
                stringMonitoredItem_schlechtTeile.SamplingInterval = 10;
                stringMonitoredItem_schlechtTeile.QueueSize = 10;
                stringMonitoredItem_schlechtTeile.Notification += OnMonitoredItemNotification;

                sub.AddItem(stringMonitoredItem_schlechtTeile);

                MonitoredItem stringMonitoredItem_input = new MonitoredItem(sub.DefaultItem);
                stringMonitoredItem_input.StartNodeId = new NodeId("ns=3;i=6199");
                stringMonitoredItem_input.AttributeId = Attributes.Value;
                stringMonitoredItem_input.DisplayName = configuration.uris[uri] + "_input";
                stringMonitoredItem_input.SamplingInterval = 10;
                stringMonitoredItem_input.QueueSize = 10;
                stringMonitoredItem_input.Notification += OnMonitoredItemNotification;

                sub.AddItem(stringMonitoredItem_input);


                // Create the monitored items on Server side
                sub.ApplyChanges();
                i++;
            }
            
            if (!int.TryParse(qty, out var iQty))
            {
                return "Invalid number of needles given.";
            }

            try
            {
                var writeResult = uaClient[0].Session.Write(null, new WriteValueCollection() { new WriteValue() { NodeId = new NodeId("ns=3;i=6199"), AttributeId = Attributes.Value, Value = new DataValue() { Value = iQty } } }, out StatusCodeCollection statusCodes, out DiagnosticInfoCollection diagnosticInfos);
                var result = uaClient[0].Session.Call(new NodeId("ns=3;i=5017"), new NodeId("ns=3;i=7012"), null);
            }
            catch (Exception ex) { Console.WriteLine("error " + ex.Message);  }
            Console.WriteLine("Starting success with " + qty + " needles");
            return "Successfully started process Stanzen";
        }

        private object DoIt(string v, MonitoredItem monitoredItem1, MonitoredItem monitoredItem2, MonitoredItemNotificationEventArgs monitoredItemNotificationEventArgs, MonitoredItemNotificationEventArgs e)
        {
            throw new NotImplementedException();
        }

        private void OnMonitoredItemNotification(MonitoredItem monitoredItem, MonitoredItemNotificationEventArgs e)
        {
            MonitoredItemNotification notification = e.NotificationValue as MonitoredItemNotification;
            Console.WriteLine("Notification: {0} \"{1}\" and Value = {2}.", notification.Message.SequenceNumber, monitoredItem.ResolvedNodeId, notification.Value);
            PropertyInfo myPropInfo = typeof(Processes).GetProperty(monitoredItem.DisplayName);
            try
            {
                if (myPropInfo.PropertyType == typeof(string))
                {
                    myPropInfo.SetValue(processes, notification.Value.Value.ToString(), null);
                }
                else
                {
                    myPropInfo.SetValue(processes, int.Parse(notification.Value.Value.ToString()), null);
                }
            }
            catch (Exception) { } // might be unitiialized yet
            
        }
    }
}