import React from 'react';
import winGrailImg from '../pictures/win_grail.png';

export default function VictoryScreen({ hero, onReturnToMenu }) {
  return (
    <div className="screen">
      <div className="panel victory" style={{ maxWidth: 480, marginTop: 40 }}>
        <div className="sprite" style={{ margin: '0 auto 16px' }}><img src={winGrailImg} alt="Victory" /></div>
        <h1>ALL KNIGHTMARES<br />DEFEATED!</h1>
        <p>All monsters conquered.<br />The Knight's legend is written.</p>

        <div className="stats-grid">
          <div className="stat-cell"><div className="label">LEVEL</div><div className="value">{hero.level}</div></div>
          <div className="stat-cell"><div className="label">TOTAL XP</div><div className="value">{hero.xp}</div></div>
          <div className="stat-cell"><div className="label">MOVES</div><div className="value">{hero.learnedMoves.length}</div></div>
          <div className="stat-cell"><div className="label">ATK</div><div className="value">{hero.stats.attack}</div></div>
          <div className="stat-cell"><div className="label">DEF</div><div className="value">{hero.stats.defense}</div></div>
          <div className="stat-cell"><div className="label">MAG</div><div className="value">{hero.stats.magic}</div></div>
        </div>

        <button className="btn" style={{ margin: '0 auto' }} onClick={onReturnToMenu}>PLAY AGAIN</button>
      </div>
    </div>
  );
}
