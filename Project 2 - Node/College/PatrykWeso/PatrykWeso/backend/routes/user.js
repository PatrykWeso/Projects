const express = require('express');
const router = express.Router();

const { requireSignin, isAuth, isAdmin } = require('../controllers/auth');

const {
  userById,
  read,
  update,
  addProductInCart,
  getProductsInCart,
} = require('../controllers/user');

router.get('/secret/:userId', requireSignin, isAuth, isAdmin, (req, res) => {
  res.json({ user: req.profile });
});

router.get('/user/:userId', requireSignin, isAuth, read);
router.put('/user/:userId', requireSignin, isAuth, update);
router.post('/user/addProductInCart', addProductInCart);
router.get('/user/ProductsInCart/:token', getProductsInCart);
router.param('userId', userById);

module.exports = router;
