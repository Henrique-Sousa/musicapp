import {Row, Col, Card, Flex} from 'antd';
import { GiMusicalScore } from "react-icons/gi";
import { PiSpeakerHighFill } from "react-icons/pi";
import { TbPiano } from "react-icons/tb";
import { LuNotebookPen } from "react-icons/lu";

import './App.less'

function App() {
  return (
    <Flex className="home" justify="center" align="center" style={{minHeight: '100vh'}} vertical>
      <Row gutter={[16, 16]} style={{marginBottom: 16}} justify="center">
        <Col>
          <Card title="Theory">
            <LuNotebookPen size="6rem"/>
          </Card>
        </Col>
        <Col>
          <Card title="Ear Training">
            <PiSpeakerHighFill size="6rem" />
          </Card>
        </Col>
      </Row>
      <Row gutter={[16, 16]} justify="center">
        <Col>
          <Card title="Score Reading">
            <GiMusicalScore size="6rem"/>
          </Card>
        </Col>
        <Col>
          <Card title="Keyboard">
            <TbPiano size="6rem" />
          </Card>
        </Col>
      </Row>
    </Flex>
  )
}

export default App
