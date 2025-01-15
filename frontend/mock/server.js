import jsonServer from 'json-server';
import bodyParser from 'body-parser';

const server = jsonServer.create();
const router = jsonServer.router('./mock/db.json');
const middlewares = jsonServer.defaults();

// JSON 파싱을 위한 미들웨어 설정
server.use(bodyParser.json());
server.use(bodyParser.urlencoded({ extended: true }));
server.use(middlewares);

// CORS 설정
server.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  next();
});

// 기존 product 라우트
server.use(jsonServer.rewriter({
  '/api/v1/product/list': '/products'
}));

// order 라우트
server.get('/api/v1/order/:email', (req, res) => {
  const email = req.params.email;
  const db = router.db.getState();
  
  if (!db.orders || !db.orders[email]) {
    res.jsonp({
      statusCode: 404,
      message: "주문을 찾을 수 없습니다",
      data: []
    });
    return;
  }

  const orderData = db.orders[email];
  res.jsonp({
    statusCode: 200,
    message: "주문 목록을 성공적으로 가져왔습니다",
    data: orderData.data.filter(order => order.email === email)
  });
});

// 주문 생성 엔드포인트
server.post('/api/v1/order', (req, res) => {
  console.log('Received body:', req.body);
  
  const orderData = req.body;
  const db = router.db.getState();
  const userEmail = orderData.email;

  if (!db.orders) {
    db.orders = {};
  }
  
  if (!db.orders[userEmail]) {
    db.orders[userEmail] = {
      statusCode: 200,
      message: "주문 목록을 성공적으로 가져왔습니다",
      data: []
    };
  }

  const lastOrderId = db.orders[userEmail].data.reduce((maxId, order) => 
    Math.max(maxId, order.orderId || 0), 0);

  const newOrder = {
    orderId: lastOrderId + 1,
    orderDate: orderData.orderDate,
    items: orderData.items,
    totalAmount: orderData.totalAmount,
    email: orderData.email,
    address: orderData.address
  };

  db.orders[userEmail].data.push(newOrder);
  router.db.setState(db);

  res.jsonp({
    statusCode: 200,
    message: "주문이 성공적으로 생성되었습니다",
    data: newOrder
  });
});

server.use(router);

server.listen(7070, () => {
  console.log('JSON Server is running on port 7070');
});