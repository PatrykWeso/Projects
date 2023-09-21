import React from 'react';
import { Link } from 'react-router-dom';
import ShowImage from './ShowImage';
import { addProductInCart } from './apiCore';

const Card = ({ product, showViewProductButton = true }) => {
  const addProductToCart = (e, product) => {
    console.log('product', product);
    const jwt = localStorage.getItem('jwt');
    addProductInCart(product._id, jwt.token).then((data) => {
      if (data.error) {
        alert('There is error adding product to cart');
      } else {
        alert('Product added to cart successfully');
      }
    });
  };
  const showViewButton = (showViewProductButton) => {
    return (
      showViewProductButton && (
        <Link to={`/product/${product._id}`} className="mr-2">
          <button className="btn btn-outline-primary mt-2 mb-2">
            View Product
          </button>
        </Link>
      )
    );
  };
  return (
    <div className="card">
      <div className="card-header"> {product.name} </div>
      <div className="card-body">
        <ShowImage item={product} url="product" />
        <p> {product.description.substring(0, 50)} </p>
        <p> {product.price} tk </p>
        {showViewButton(showViewProductButton)}

        <button
          className="btn btn-outline-warning mt-2 mb-2"
          onClick={(e) => addProductToCart(e, product)}
        >
          Add to cart
        </button>
      </div>
    </div>
  );
};

export default Card;
