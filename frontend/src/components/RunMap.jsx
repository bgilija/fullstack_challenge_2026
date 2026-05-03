import React from 'react';
import { getMoveType } from '../combat.js';
import knightImg from '../pictures/knight.png';

const getMonsterImage = (name) =>
  new URL(`../pictures/${name.toLowerCase().replace(/ /g, '_')}.png`, import.meta.url).href;

export default function RunMap({ runConfig, hero, defeatedMonsters, onEnterBattle, onOpenMoveManager, onReturnToMenu }) {
  const { monsters, moves, hero: hcfg } = runConfig;

  const xpThresholds = hcfg.xpThresholds;
  let xpCurrent = hero.xp, xpNext = null, xpPrev = 0;
  for (const t of xpThresholds) {
    if (hero.xp < t) { xpNext = t; break; }
    xpPrev = t;
  }
  const xpPct = xpNext ? ((xpCurrent - xpPrev) / (xpNext - xpPrev)) * 100 : 100;

  return (
    <div className="screen">
      <div className="panel">
        <div className="panel-title">THE KNIGHTMARES</div>

        {/* Hero info */}
        <div className="hero-bar">
          <div className="sprite"><img src={knightImg} alt="Knight" /></div>
          <div className="hero-details">
            <h2>{hcfg.name}  <span style={{ color: 'var(--dim)', fontSize: 9 }}>LV {hero.level}</span></h2>
            <div className="hero-stats-row">
              <span>HP {hero.maxHp}</span>
              <span>ATK {hero.stats.attack}</span>
              <span>DEF {hero.stats.defense}</span>
              <span>MAG {hero.stats.magic}</span>
            </div>
            <div className="xp-label">
              {xpNext ? `XP ${hero.xp} / ${xpNext}` : 'MAX LEVEL'}
            </div>
            <div className="hp-bar" style={{ marginTop: 3 }}>
              <div className="hp-fill" style={{ width: `${xpPct}%`, background: 'var(--gold)' }} />
            </div>
          </div>
        </div>

        {/* Equipped moves + manage button */}
        <div className="equipped-section">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 }}>
            <h3 style={{ margin: 0 }}>EQUIPPED MOVES</h3>
            <button className="btn-sm" onClick={onOpenMoveManager}>MANAGE</button>
          </div>
          <div className="equipped-grid">
            {hero.equippedMoves.map(id => {
              const m = moves[id];
              if (!m) return null;
              const t = getMoveType(m);
              return (
                <div key={id} className="equipped-move">
                  <span>{m.name}</span>
                  {t && <span className={`type-badge ${t}`}>{t === 'physical' ? 'PHY' : 'MAG'}</span>}
                </div>
              );
            })}
          </div>
        </div>

        <hr className="divider" />

        {/* Encounter list */}
        <div className="encounter-list">
          {monsters.map((mon, idx) => {
            const done   = defeatedMonsters.includes(idx);
            const isNext = !done && (idx === 0 || defeatedMonsters.includes(idx - 1));
            const locked = !done && !isNext;
            const cls    = `encounter ${done ? 'enc-done' : ''} ${isNext ? 'enc-next' : ''} ${locked ? 'enc-locked' : ''}`;

            return (
              <div key={mon.id} className={cls}>

                {/* Header row — click to fight */}
                <div
                  className="enc-header"
                  onClick={() => !locked && onEnterBattle(idx)}
                  style={{ cursor: locked ? 'default' : 'pointer' }}
                >
                  <span className="enc-num">{idx + 1}</span>
                  <span className="enc-sprite"><img src={getMonsterImage(mon.name)} alt={mon.name} /></span>
                  <div className="enc-info">
                    <div className="enc-name">{mon.name}</div>
                    <div className="enc-stats">
                      HP {mon.stats.health} · ATK {mon.stats.attack} · DEF {mon.stats.defense} · MAG {mon.stats.magic}
                    </div>
                  </div>
                  {done   && <span className="enc-tag defeated">DONE</span>}
                  {isNext && <span className="enc-tag fight">FIGHT</span>}
                  {locked && <span className="enc-tag locked">???</span>}
                </div>

                {/* Move list — only shown for the next enemy to fight */}
                {isNext && (
                  <div className="enc-moves">
                    {mon.moves.map(moveId => {
                      const m = moves[moveId];
                      if (!m) return null;
                      const t = getMoveType(m);
                      return (
                        <div key={moveId} className="enc-move-row">
                          <span className="enc-move-name">{m.name}</span>
                          <span className={`cat-label ${m.category}`}>{m.category}</span>
                          {t && <span className={`type-badge ${t}`}>{t === 'physical' ? 'PHY' : 'MAG'}</span>}
                          <span className="enc-move-desc">{m.description.split('.')[0]}</span>
                        </div>
                      );
                    })}
                  </div>
                )}

              </div>
            );
          })}
        </div>

        <hr className="divider" />

        <div className="map-actions">
          <button className="btn-sm" onClick={onReturnToMenu}>MENU</button>
        </div>
      </div>
    </div>
  );
}
