/*

Copyright (c) 2024 Fraunhofer-Institut f&uuml;r Produktionstechnik und Automatisierung IPA

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

*/
namespace ProDaTexUI.Server
{
    public class Processes
    {
        public string activeStanzen_state { get; set; } = "unknown";
        public int activeStanzen_input { get; set; } = 0;
        public int activeStanzen_gutTeile { get; set; } = 0;
        public int activeStanzen_schlechtTeile { get; set; } = 0;
        public string activeSchleifen_state { get; set; } = "unknown";
        public int activeSchleifen_input { get; set; } = 0;
        public int activeSchleifen_gutTeile { get; set; } = 0;
        public int activeSchleifen_schlechtTeile { get; set; } = 0;
        public string activeFraesen_state { get; set; } = "unknown";
        public int activeFraesen_input { get; set; } = 0;
        public int activeFraesen_gutTeile { get; set; } = 0;
        public int activeFraesen_schlechtTeile { get; set; } = 0;
        public string activeHaerten_state { get; set; } = "unknown";
        public int activeHaerten_input { get; set; } = 0;
        public int activeHaerten_gutTeile { get; set; } = 0;
        public int activeHaerten_schlechtTeile { get; set; } = 0;
        public string activeRichten_state { get; set; } = "unknown";
        public int activeRichten_input { get; set; } = 0;
        public int activeRichten_gutTeile { get; set; } = 0;
        public int activeRichten_schlechtTeile { get; set; } = 0;

    }
}