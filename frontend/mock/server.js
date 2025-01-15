import jsonServer from 'json-server';
const server = jsonServer.create();
const router = jsonServer.router('./mock/db.json');
const middlewares = jsonServer.defaults();

server.use(middlewares);

// 기존 product 라우트
server.use(jsonServer.rewriter({
  '/api/v1/product/list': '/products'
}));

// order 라우트 추가
server.get('/api/v1/order/:email', (req, res) => {
  const email = req.params.email;
  const db = router.db.getState();
  
  const orderData = db.orders[email];
  
  if (orderData) {
    res.jsonp(orderData);
  } else {
    res.jsonp({
      statusCode: 404,
      message: "주문을 찾을 수 없습니다",
      data: []
    });
  }
});

server.use(router);

server.listen(7070, () => {
  console.log('JSON Server is running on port 7070');
});