import React from 'react';
import { getMoveType } from '../combat.js';

export default function PostBattle({ result, hero, monster, moves, onContinue, onReturnToMenu }) {
  const { won, learnedMoveId, xpGained, leveledUp, newLevel } = result;
  const learnedMove = learnedMoveId ? moves[learnedMoveId] : null;

  if (!won) {
    return (
      <div className="screen">
        <div className="panel post-battle" style={{ maxWidth: 480 }}>
          <div className="post-title loss">DEFEATED</div>
          <p className="post-subtitle">{monster.name} was too powerful.</p>
          <p style={{ fontSize: 8, color: 'var(--dim)', marginBottom: 20 }}>
            Manage your moves and try again.
          </p>
          <div className="post-actions">
            <button className="btn" onClick={onContinue}>TRY AGAIN</button>
            <button className="btn-sm" onClick={onReturnToMenu}>MENU</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="screen">
      <div className="panel post-battle" style={{ maxWidth: 480 }}>
        <div className="post-title win">VICTORY!</div>
        <p className="post-subtitle">{monster.name} defeated!</p>

        <div className="reward-box">
          <div className="reward-row">
            <span>XP GAINED</span>
            <span className="xp-val">+{xpGained}</span>
          </div>
          {leveledUp && (
            <div className="reward-row" style={{ borderTop: '1px solid var(--border)', marginTop: 6, paddingTop: 6 }}>
              <span>LEVEL UP!</span>
              <span className="lv-val">LV {newLevel}</span>
            </div>
          )}
        </div>

        {learnedMove && (
          <div className="learned-box">
            <h3>NEW MOVE LEARNED</h3>
            <div className="move-name-lg">
              {learnedMove.name}
              {getMoveType(learnedMove) && (
                <span className={`type-badge ${getMoveType(learnedMove)}`} style={{ marginLeft: 8 }}>
                  {getMoveType(learnedMove) === 'physical' ? 'PHY' : 'MAG'}
                </span>
              )}
            </div>
            <p>{learnedMove.description}</p>
            <p style={{ marginTop: 6, fontSize: 7, color: 'var(--dim)' }}>
              Available in Move Manager.
            </p>
          </div>
        )}

        {!learnedMove && learnedMoveId === null && (
          <div className="reward-box" style={{ marginBottom: 14 }}>
            <p style={{ fontSize: 8, color: 'var(--dim)' }}>
              You already know all of {monster.name}'s moves.
            </p>
          </div>
        )}

        <div className="post-actions">
          <button className="btn" onClick={onContinue}>CONTINUE</button>
        </div>
      </div>
    </div>
  );
}
