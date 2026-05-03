import React, { useState } from 'react';
import { getMoveType } from '../combat.js';

export default function MoveManager({ hero, moves, onEquipMove, onClose }) {
  const [selectedSlot, setSelectedSlot] = useState(null);

  function pickSlot(i) { setSelectedSlot(i === selectedSlot ? null : i); }

  function pickMove(id) {
    if (selectedSlot === null) return;
    onEquipMove(id, selectedSlot);
    setSelectedSlot(null);
  }

  return (
    <div className="screen">
      <div className="panel">
        <div className="panel-title">MOVE MANAGER</div>

        <p className="mm-hint">
          {selectedSlot === null
            ? 'Click a slot to select it, then pick a move below.'
            : `Slot ${selectedSlot + 1} selected — pick a move to equip.`}
        </p>

        <div className="mm-section">EQUIPPED (4 SLOTS)</div>
        <div className="slot-grid">
          {hero.equippedMoves.map((id, i) => {
            const m = moves[id];
            const t = m ? getMoveType(m) : null;
            return (
              <button key={i} className={`slot-btn ${selectedSlot === i ? 'selected' : ''}`} onClick={() => pickSlot(i)}>
                <span className="slot-num">{i + 1}</span>
                <span style={{ fontSize: 9 }}>{m ? m.name : '—'}</span>
                {t && <span className={`type-badge ${t}`} style={{ marginLeft: 'auto' }}>{t === 'physical' ? 'PHY' : 'MAG'}</span>}
              </button>
            );
          })}
        </div>

        <div className="mm-section">ALL LEARNED MOVES ({hero.learnedMoves.length})</div>
        <div className="learned-list">
          {hero.learnedMoves.map(id => {
            const m = moves[id];
            if (!m) return null;
            const equipped = hero.equippedMoves.includes(id);
            const t = getMoveType(m);
            return (
              <button
                key={id}
                className={`learned-btn ${equipped ? 'equipped' : ''} ${selectedSlot !== null ? 'pickable' : ''}`}
                disabled={selectedSlot === null}
                onClick={() => pickMove(id)}
              >
                <div className="lm-top">
                  <span className="lm-name">{m.name}</span>
                  <span className={`cat-label ${m.category}`}>{m.category}</span>
                  {t && <span className={`type-badge ${t}`}>{t === 'physical' ? 'PHY' : 'MAG'}</span>}
                  {equipped && <span className="eq-tag">EQ</span>}
                </div>
                <div className="lm-desc">{m.description}</div>
              </button>
            );
          })}
        </div>

        <div className="mm-footer">
          <button className="btn" onClick={onClose}>DONE</button>
        </div>
      </div>
    </div>
  );
}
