import React, { useState, useEffect } from 'react';
import Layout from './Layout';
import { read, listRelated } from './apiCore';
import Card from './Card';
import Carousel from './Carousel';

const Product = (props) => {
  const [product, setProduct] = useState({});
  const [error, setError] = useState(false);

  const loadSingleProduct = (productId) => {
    read(productId).then((data) => {
      if (data.error) {
        setError(data.error);
      } else {
        setProduct(data);
      }
    });
  };

  useEffect(() => {
    const productId = props.match.params.productId;
    loadSingleProduct(productId);
    console.log('In load single product component');
  }, [props]);

  return (
    <div>
      {/* <Carousel /> */}
      <Layout
        title={product && product.name}
        description={
          product &&
          product.description &&
          product.description.substring(0, 100)
        }
        className="container-fluid"
      >
        <div className="row">
          <div className="col-8">
            {product && product.description && (
              <Card product={product} showViewProductButton={false} />
            )}
          </div>

          {/* <div className="col-4">
                    <h4>Related products</h4>
                    {relatedProduct.map((p, i) => (
                        <div className="mb-3" key={i}>
                            <Card product={p} />
                        </div>
                    ))}
                </div> */}
        </div>
      </Layout>
    </div>
  );
};

export default Product;
