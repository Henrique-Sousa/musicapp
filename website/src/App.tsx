import {Row, Col, Card, Flex} from 'antd';

import './App.css'

function App() {
  return (
    <Flex className="home" justify="center" align="center" style={{height: '100vh'}} vertical>
      <Row gutter={16} style={{marginBottom: 16}} justify="center">
        <Col>
          <Card title="Theory">
            Image here
          </Card>
        </Col>
        <Col>
          <Card title="Ear training">
            Image here
          </Card>
        </Col>
      </Row>
      <Row gutter={16} justify="center">
        <Col>
          <Card title="Score reading">
            Image here
          </Card>
        </Col>
        <Col>
          <Card title="Piano">
            Image here
          </Card>
        </Col>
      </Row>
    </Flex>
  )
}

export default App
