/*

Copyright (c) 2024 Fraunhofer-Institut f&uuml;r Produktionstechnik und Automatisierung IPA

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

*/

import { useEffect, useState } from 'react';
import { Row, Col, Container, Alert, Button, Table, Input } from 'reactstrap';
import { SvgLoader, SvgProxy } from 'react-svgmt';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {



    useEffect(() => {
        const interval = setInterval(() => getProcessData(), 1000);
        return () => {
            clearInterval(interval);
        };
    }, []);

    useEffect(() => {
        setActiveFraesen("unknown");
        setActiveHaerten("unknown");
        setActiveRichten("unknown");
        setActiveStanzen("unknown");
        setActiveSchleifen("unknown");
        getProcessData();

    }, []);

    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState("Welcome");
    const [activeFraesen, setActiveFraesen] = useState("Unknown");
    const [activeHaerten, setActiveHaerten] = useState("Unknown");
    const [activeRichten, setActiveRichten] = useState("Unknown");
    const [activeStanzen, setActiveStanzen] = useState("Unknown");
    const [activeSchleifen, setActiveSchleifen] = useState("Unknown");
    const [stanzen_gut, setStanzen_gut] = useState(0);
    const [stanzen_schlecht, setStanzen_schlecht] = useState(0);
    const [fraesen_schlecht, setFraesen_schlecht] = useState(0);
    const [fraesen_gut, setFraesen_gut] = useState(0);
    const [haerten_schlecht, setHaerten_schlecht] = useState(0);
    const [haerten_gut, setHaerten_gut] = useState(0);
    const [richten_schlecht, setRichten_schlecht] = useState(0);
    const [richten_gut, setRichten_gut] = useState(0);
    const [schleifen_schlecht, setSchleifen_schlecht] = useState(0);
    const [schleifen_gut, setSchleifen_gut] = useState(0);
    const [auftragsmnege, setAuftragsmenge] = useState("0");

    const [activeFraesenInput, setActiveFraesenInput] = useState(0);
    const [activeHaertenInput, setActiveHaertenInput] = useState(0);
    const [activeRichtenInput, setActiveRichtenInput] = useState(0);
    const [activeStanzenInput, setActiveStanzenInput] = useState(0);
    const [activeSchleifenInput, setActiveSchleifenInput] = useState(0);




    return (loading) ? (<div>Loading</div>) :
        (
            <Container>
                <img src="logo.gif" width="180" /><p />

                <Alert color="primary"><h2>ProDaTex AAS Process emulation demonstrator</h2>

                </Alert>
                <Row xs="2">
                    <Col style={{ textAlign: 'left' }}>
                        <b>Start:</b> Enter number of needles to produce and press start</Col>
                    <Col xs="2">


                        <Input
                            id="qty"
                            name="qty"
                            placeholder="Enter number of needles here"
                            type="number"
                            value={auftragsmnege}
                            onChange={e => setAuftragsmenge(e.target.value)}
                        />
                    </Col>
                    <Col xs="2" style={{ textAlign: 'left' }}>
                        <Button style={{ width: '150px' }} size="sm" color="success" onClick={() => { startProcessing() }}>
                            Start processing
                        </Button></Col>



                </Row>
                <Row><Col>&nbsp;</Col></Row>
                <Row>


                    <Col>

                        <SvgLoader width="280" path="machinery.svg">
                            <SvgProxy selector={"rect"} fill="white" />
                            <SvgProxy selector={"#rect144"} fill="#d9d9d9" />
                            <SvgProxy selector={"#rect150"} fill="#bfbfbf" />
                            <SvgProxy selector={"#text107"}>Stanzen</SvgProxy>
                            <SvgProxy selector={"#" + activeStanzen} fill="#add8e6" />
                        </SvgLoader>
                        <Table bordered
                            size="sm">
                            <tbody>
                                <tr>
                                    <th scope="row">
                                        Eingangsmenge
                                    </th>
                                    <td>
                                        {activeStanzenInput}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Gutteile
                                    </th>
                                    <td>
                                        {stanzen_gut}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Schlechtteile
                                    </th>
                                    <td>
                                        {stanzen_schlecht}
                                    </td>
                                </tr></tbody>
                        </Table>

                    </Col>
                    <Col>
                        <SvgLoader width="280" path="machinery.svg">
                            <SvgProxy selector={"rect"} fill="white" />
                            <SvgProxy selector={"#rect144"} fill="#d9d9d9" />
                            <SvgProxy selector={"#rect150"} fill="#bfbfbf" />
                            <SvgProxy selector={"#text107"}>Schleifen</SvgProxy>
                            <SvgProxy selector={"#" + activeSchleifen} fill="#add8e6" />
                        </SvgLoader>
                        <Table bordered
                            size="sm">
                            <tbody>
                                <tr>
                                    <th scope="row">
                                        Eingangsmenge
                                    </th>
                                    <td>
                                        {activeSchleifenInput}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Gutteile
                                    </th>
                                    <td>
                                        {schleifen_gut}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Schlechtteile
                                    </th>
                                    <td>
                                        {schleifen_schlecht}
                                    </td>
                                </tr>

                            </tbody>
                        </Table>
                    </Col>
                    <Col>
                        <SvgLoader width="280" path="machinery.svg">
                            <SvgProxy selector={"rect"} fill="white" />
                            <SvgProxy selector={"#rect144"} fill="#d9d9d9" />
                            <SvgProxy selector={"#rect150"} fill="#bfbfbf" />
                            <SvgProxy selector={"#text107"}>Fraesen</SvgProxy>
                            <SvgProxy selector={"#" + activeFraesen} fill="#add8e6" />
                        </SvgLoader>
                        <Table bordered
                            size="sm">
                            <tbody>
                                <tr>
                                    <th scope="row">
                                        Eingangsmenge
                                    </th>
                                    <td>
                                        {activeFraesenInput}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Gutteile
                                    </th>
                                    <td>
                                        {fraesen_gut}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Schlechtteile
                                    </th>
                                    <td>
                                        {fraesen_schlecht}
                                    </td>
                                </tr></tbody>
                        </Table>
                    </Col>
                    <Col>
                        <SvgLoader width="280" path="machinery.svg">
                            <SvgProxy selector={"rect"} fill="white" />
                            <SvgProxy selector={"#rect144"} fill="#d9d9d9" />
                            <SvgProxy selector={"#rect150"} fill="#bfbfbf" />
                            <SvgProxy selector={"#text107"}>Haerten</SvgProxy>
                            <SvgProxy selector={"#" + activeHaerten} fill="#add8e6" />
                        </SvgLoader>
                        <Table bordered
                            size="sm">
                            <tbody>
                                
                                <tr>
                                    <th scope="row">
                                        Eingangsmenge
                                    </th>
                                    <td>
                                        {activeHaertenInput}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Gutteile
                                    </th>
                                    <td>
                                        {haerten_gut}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Schlechtteile
                                    </th>
                                    <td>
                                        {haerten_schlecht}
                                    </td>
                                </tr></tbody>
                        </Table>
                    </Col>
                    <Col>
                        <SvgLoader width="280" path="machinery.svg">
                            <SvgProxy selector={"rect"} fill="white" />
                            <SvgProxy selector={"#rect144"} fill="#d9d9d9" />
                            <SvgProxy selector={"#rect150"} fill="#bfbfbf" />
                            <SvgProxy selector={"#text107"}>Richten</SvgProxy>
                            <SvgProxy selector={"#" + activeRichten} fill="#add8e6" />
                        </SvgLoader>
                        <Table bordered
                            size="sm">
                            <tbody>
                                <tr>
                                    <th scope="row">
                                        Eingangsmenge
                                    </th>
                                    <td>
                                        {activeRichtenInput}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Gutteile
                                    </th>
                                    <td>
                                        {richten_gut}
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        Schlechtteile
                                    </th>
                                    <td>
                                        {richten_schlecht}
                                    </td>
                                </tr></tbody>
                        </Table>
                    </Col>

                </Row>
                <Alert color="secondary">{message}

                </Alert>
                <Row xs="2">
                    <Col xs="2">
                        <p style={{ writingMode: 'vertical-lr' }}><b>partners<br />Development</b></p>
                    </Col>
                    <Col>
                    <Row>
               
                                d-opt GmbH
               </Row><Row>
                                Technische Universit&auml;t Chemnitz, Professur Fabrikplanung und Intralogistik
                        </Row><Row> 
                                Amorph Systems GmbH
                        </Row><Row>
                            Fraunhofer-Institut f&uuml;r Produktionstechnik und Automatisierung IPA
                        </Row>
                    </Col>
                    </Row>
                <Row>
                    <b><i>Copyright 2024 by Fraunhofer-Institut f&uuml;r Produktionstechnik und Automatisierung IPA</i></b>
                </Row>
                

            </Container>


        );

      // Gets data from the backend including all states of the emulated machines and its Gutteile/Schlechteile
      async function getProcessData() {
        const response = await fetch('processes');
        const data = await response.json();
        setActiveStanzen(data.activeStanzen_state);
        setActiveFraesen(data.activeFraesen_state);
        setActiveHaerten(data.activeHaerten_state);
        setActiveRichten(data.activeRichten_state);
        setActiveSchleifen(data.activeSchleifen_state);

        setActiveFraesenInput(data.activeFraesen_input);
        setActiveHaertenInput(data.activeHaerten_input);
        setActiveRichtenInput(data.activeRichten_input);
        setActiveSchleifenInput(data.activeSchleifen_input);
        setActiveStanzenInput(data.activeStanzen_input);

        setStanzen_gut(data.activeStanzen_gutTeile);
        setStanzen_schlecht(data.activeStanzen_schlechtTeile);
        setFraesen_gut(data.activeFraesen_gutTeile);
        setFraesen_schlecht(data.activeFraesen_schlechtTeile);
        setHaerten_gut(data.activeHaerten_gutTeile);
        setHaerten_schlecht(data.activeHaerten_schlechtTeile);
        setRichten_gut(data.activeRichten_gutTeile);
        setRichten_schlecht(data.activeRichten_schlechtTeile);
        setSchleifen_gut(data.activeSchleifen_gutTeile);
        setSchleifen_schlecht(data.activeSchleifen_schlechtTeile);

        setLoading(false);
    }

    // Sends a start command to the backend including the number of needles
    async function startProcessing() {
        const response = await fetch('processes/start/' + auftragsmnege);
        setMessage(await response.text());
    }
}

export default App;