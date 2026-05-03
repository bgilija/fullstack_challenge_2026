import React, { useState } from 'react';
import { getMoveType } from '../combat.js';
import { postBattleTurn } from '../api.js';
import knightImg from '../pictures/knight.png';
import skullDeathImg from '../pictures/skull_death.png';

const getMonsterImage = (name) =>
  new URL(`../pictures/${name.toLowerCase().replace(/ /g, '_')}.png`, import.meta.url).href;

function hpFill(pct) {
  if (pct > 0.55) return 'var(--hp-hi)';
  if (pct > 0.25) return 'var(--hp-mid)';
  return 'var(--hp-lo)';
}

function HpBar({ hp, maxHp }) {
  const pct = Math.max(0, hp / maxHp) * 100;
  return (
    <div className="hp-row">
      <div className="hp-bar"><div className="hp-fill" style={{ width: `${pct}%`, background: hpFill(pct / 100) }} /></div>
      <span className="hp-text">{hp}/{maxHp}</span>
    </div>
  );
}

function BuffList({ buffs }) {
  if (!buffs.length) return null;
  return (
    <div className="buffs-row">
      {buffs.map((b, i) => (
        <span key={i} className={`buff-tag ${b.percent > 0 ? 'pos' : 'neg'}`}>
          {b.percent > 0 ? '+' : ''}{b.percent}% {b.stat} ({b.turnsLeft}t)
        </span>
      ))}
    </div>
  );
}

export default function BattleScreen({ hero, monster, moves, monsterIndex, totalMonsters, onBattleEnd }) {
  const [heroHp,      setHeroHp]      = useState(hero.maxHp);
  const [monsterHp,   setMonsterHp]   = useState(monster.stats.health);
  const [heroBuffs,   setHeroBuffs]   = useState([]);
  const [monsterBuffs,setMonsterBuffs]= useState([]);
  const [log,         setLog]         = useState([`Battle starts! ${hero.name || 'Knight'} vs ${monster.name}`]);
  const [phase,       setPhase]       = useState('hero_turn');
  const [turn,        setTurn]        = useState(1);
  const [heroDead,    setHeroDead]    = useState(false);

  const addLog = msg => setLog(prev => [...prev.slice(-5), msg]);

  async function handleMove(moveId) {
    if (phase !== 'hero_turn') return;
    setPhase('processing');
    try {
      const res = await postBattleTurn({
        heroMoveId: moveId,
        monsterId: monster.id,
        heroHp, heroMaxHp: hero.maxHp,
        heroAttack: hero.stats.attack,
        heroDefense: hero.stats.defense,
        heroMagic: hero.stats.magic,
        heroBuffs, monsterHp, monsterBuffs, turn,
        heroXp: hero.xp,
        heroLevel: hero.level,
        heroLearnedMoves: hero.learnedMoves,
        monsterIndex,
        totalMonsters
      });
      if (res.heroLog) addLog('> ' + res.heroLog);

      await new Promise(r => setTimeout(r, 400));

      setHeroHp(res.heroHpAfter);
      setMonsterHp(res.monsterHpAfter);
      setHeroBuffs(res.heroBuffsAfter);
      setMonsterBuffs(res.monsterBuffsAfter);

      if (res.winner === 'hero') {
        addLog(`${monster.name} defeated!`);
        setPhase('over');
        setTimeout(() => onBattleEnd({
          won: true,
          learnedMoveId: res.learnedMoveId,
          runComplete: res.runComplete,
          xpGained: res.xpGained,
          newXp: res.newXp,
          newLevel: res.newLevel,
          leveledUp: res.leveledUp,
          newMaxHp: res.newMaxHp,
          newStats: { attack: res.newAttack, defense: res.newDefense, magic: res.newMagic }
        }), 600);
      } else if (res.winner === 'monster') {
        if (res.monsterLog) addLog('> ' + res.monsterLog);
        setHeroDead(true);
        setPhase('over');
        setTimeout(() => onBattleEnd({ won: false, learnedMoveId: null, runComplete: false, xpGained: 0 }), 600);
      } else {
        if (res.monsterLog) addLog('> ' + res.monsterLog);
        setTurn(res.nextTurnNumber);
        setPhase('hero_turn');
      }
    } catch {
      setPhase('hero_turn');
    }
  }

  return (
    <div className="screen">
      <div className="panel battle-screen">
        <div className="battle-header">
          <span>TURN {turn}</span>
          <span>{hero.name || 'Knight'} VS {monster.name}</span>
        </div>

        {/* Monster */}
        <div className="char-row enemy">
          <div className="sprite"><img src={getMonsterImage(monster.name)} alt={monster.name} /></div>
          <div className="char-info-col">
            <div className="char-name">{monster.name}</div>
            <HpBar hp={monsterHp} maxHp={monster.stats.health} />
            <BuffList buffs={monsterBuffs} />
          </div>
        </div>

        {/* Battle log */}
        <div className="battle-log">
          {log.map((msg, i) => (
            <div key={i} className={`log-line ${i === log.length - 1 ? 'latest' : ''}`}>{msg}</div>
          ))}
        </div>

        {/* Hero */}
        <div className="char-row">
          <div className="sprite"><img src={heroDead ? skullDeathImg : knightImg} alt="Knight" /></div>
          <div className="char-info-col">
            <div className="char-name">{hero.name || 'Knight'}  LV {hero.level}</div>
            <HpBar hp={heroHp} maxHp={hero.maxHp} />
            <BuffList buffs={heroBuffs} />
          </div>
        </div>

        {/* Move buttons */}
        <div className="move-grid">
          {phase === 'processing' && (
            <div className="processing-overlay">Processing turn...</div>
          )}
          {hero.equippedMoves.map(id => {
            const m = moves[id];
            if (!m) return null;
            const t = getMoveType(m);
            return (
              <button key={id} className="move-btn" disabled={phase !== 'hero_turn'} onClick={() => handleMove(id)}>
                <div className="move-btn-top">
                  <span className="move-btn-name">{m.name}</span>
                  {t && <span className={`type-badge ${t}`}>{t === 'physical' ? 'PHY' : 'MAG'}</span>}
                </div>
                <span className={`cat-label ${m.category}`}>{m.category}</span>
                <div className="move-btn-desc">{m.description.split('.')[0]}</div>
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
}
