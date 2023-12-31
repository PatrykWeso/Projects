import React, { Fragment } from 'react';

const img1 = require('./Merchendise-1.jpg');
const img2 = require('./Merchendise-2.jpeg');
const img3 = require('./Merchendise-3.jpg');
const img4 = require('./Merchendise-4.jpg');
const img5 = require('./Merchendise-5.jpeg');

const Carousel = () => {
  return (
    <div>
      <div
        id="carouselExampleIndicators"
        class="carousel slide"
        data-ride="carousel"
      >
        <ol class="carousel-indicators">
          <li
            data-target="#carouselExampleIndicators"
            data-slide-to="0"
            class="active"
          ></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>

        <div class="carousel-inner">
          <div class="carousel-item active">
            <img class="d-block w-100" src={img1} alt="First slide" />
          </div>
          <div class="carousel-item">
            <img class="d-block w-100" src={img2} alt="Second slide" />
          </div>
          <div class="carousel-item">
            <img class="d-block w-100" src={img3} alt="Third slide" />
          </div>
          <div class="carousel-item">
            <img class="d-block w-100" src={img4} alt="Third slide" />
          </div>
          <div class="carousel-item">
            <img class="d-block w-100" src={img5} alt="Third slide" />
          </div>
        </div>

        <a
          class="carousel-control-prev"
          href="#carouselExampleIndicators"
          role="button"
          data-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a
          class="carousel-control-next"
          href="#carouselExampleIndicators"
          role="button"
          data-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
    </div>
  );
};

export default Carousel;
