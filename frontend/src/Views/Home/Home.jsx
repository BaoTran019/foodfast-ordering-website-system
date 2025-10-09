import React from 'react'
import "./Home.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import Hero from '../../components/Hero/Hero';
import FoodSwiper from '../../Menu/components/Swiper/FoodSwiper';
import { Button } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

export default function Home() {

  return (
    <>
      <div className='home'>

        {/* Hero banner section */}
        <Hero />

        {/** Home page content */}
        <div className='home-content'>
          <h1 className='content-title'>Hãy lựa chọn món bạn thích nhé !</h1>
          <FoodSwiper category='Fried_Chicken'></FoodSwiper>
          <Button
           as={NavLink} to='/menu'
           onClick={() => window.scrollTo(0, 0)}
           style={{
            backgroundColor:'#ff8c09',
            border:'1px solid #ff8c09',
            marginTop:'1rem',
            fontSize:'large'
           }}>Xem thêm món ăn</Button>
        </div>
      </div>
    </>
  )
}
