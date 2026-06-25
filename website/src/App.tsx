import {Row, Col, Card} from 'antd';

import './App.css'

function App() {
  return (
    <>
      <Row gutter={[16, 16]}>
        <Col span={6}>
          <Card title="Theory">
            Image here
          </Card>
        </Col>
        <Col span={6}>
          <Card title="Eart training">
            Image here
          </Card>
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        <Col span={6}>
          <Card title="Score reading">
            Image here
          </Card>
        </Col>
        <Col span={6}>
          <Card title="Piano">
            Image here
          </Card>
        </Col>
      </Row>
    </>
  )
}

export default App
