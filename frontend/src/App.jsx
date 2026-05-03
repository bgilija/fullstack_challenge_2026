import React, { useReducer } from 'react';
import './index.css';
import { gameReducer, initialState, PHASES } from './gameReducer.js';
import { fetchConfig } from './api.js';
import MainMenu from './components/MainMenu.jsx';
import RunMap from './components/RunMap.jsx';
import BattleScreen from './components/BattleScreen.jsx';
import PostBattle from './components/PostBattle.jsx';
import MoveManager from './components/MoveManager.jsx';
import VictoryScreen from './components/VictoryScreen.jsx';

export default function App() {
  const [state, dispatch] = useReducer(gameReducer, initialState);

  async function handleStartRun() {
    dispatch({ type: 'START_LOADING' });
    try {
      const config = await fetchConfig();
      dispatch({ type: 'CONFIG_LOADED', config });
    } catch (err) {
      console.error(err);
      alert('Unable to connect to the server.');
      dispatch({ type: 'RETURN_TO_MENU' });
    }
  }

  const { phase, runConfig, hero, defeatedMonsters, currentMonsterIndex, lastBattleResult } = state;

  if (phase === PHASES.LOADING) {
    return (
      <div className="app">
        <div className="loading-screen">
          <div className="spinner" />
          <span>Loading adventure...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="app">
      {phase === PHASES.MENU && (
        <MainMenu onStart={handleStartRun} />
      )}

      {phase === PHASES.RUN_OVERVIEW && (
        <RunMap
          runConfig={runConfig}
          hero={hero}
          defeatedMonsters={defeatedMonsters}
          onEnterBattle={idx => dispatch({ type: 'ENTER_BATTLE', monsterIndex: idx })}
          onOpenMoveManager={() => dispatch({ type: 'OPEN_MOVE_MANAGER' })}
          onReturnToMenu={() => dispatch({ type: 'RETURN_TO_MENU' })}
        />
      )}

      {phase === PHASES.MOVE_MANAGER && (
        <MoveManager
          hero={hero}
          moves={runConfig.moves}
          onEquipMove={(moveId, slotIndex) => dispatch({ type: 'EQUIP_MOVE', moveId, slotIndex })}
          onClose={() => dispatch({ type: 'CLOSE_MOVE_MANAGER' })}
        />
      )}

      {phase === PHASES.BATTLE && (
        <BattleScreen
          hero={hero}
          monster={runConfig.monsters[currentMonsterIndex]}
          moves={runConfig.moves}
          monsterIndex={currentMonsterIndex}
          totalMonsters={runConfig.monsters.length}
          onBattleEnd={(result) => dispatch({ type: 'BATTLE_END', ...result })}
        />
      )}

      {phase === PHASES.POST_BATTLE && (
        <PostBattle
          result={lastBattleResult}
          hero={hero}
          monster={runConfig.monsters[currentMonsterIndex]}
          moves={runConfig.moves}
          onContinue={() => dispatch({ type: 'CONTINUE_FROM_POST_BATTLE' })}
          onReturnToMenu={() => dispatch({ type: 'RETURN_TO_MENU' })}
        />
      )}

      {phase === PHASES.VICTORY && (
        <VictoryScreen
          hero={hero}
          onReturnToMenu={() => dispatch({ type: 'RETURN_TO_MENU' })}
        />
      )}
    </div>
  );
}
