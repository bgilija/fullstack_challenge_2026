import React from 'react';
import swordsImg from '../pictures/swords_main_menu.png';

export default function MainMenu({ onStart }) {
  return (
    <div className="screen">
      <div className="panel main-menu" style={{ maxWidth: 480, marginTop: 60 }}>
        <h1>THE<br />KNIGHTMARES</h1>
        <div className="sprite" style={{ margin: '20px auto' }}><img src={swordsImg} alt="Swords" /></div>
        <p className="tagline">Face The Knightmares.<br />Learn their moves.<br />Master your fate.</p>
        <button className="btn" onClick={onStart}>START GAME</button>
      </div>
    </div>
  );
}
