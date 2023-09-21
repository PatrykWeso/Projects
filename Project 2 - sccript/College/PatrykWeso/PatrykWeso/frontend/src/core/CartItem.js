import React, { useEffect, useState } from 'react';
import { getProductInCart } from './apiCore';

function CartItem(props) {
  const [products, setProducts] = useState();
  const getCartItems = async (token) => {
    await getProductInCart(token).then((data) => {
      if (data?.error) {
        alert('Error while fetching products in cart');
      } else {
        setProducts(data);
        console.log(products);
      }
    });
  };
  useEffect(() => {
    const token = props.match.params.token;
    console.log(token);
    getCartItems(token);
  }, []);
  return (
    <div>
      <p>Car items</p>
    </div>
  );
}

export default CartItem;
