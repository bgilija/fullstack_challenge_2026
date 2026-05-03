
export const PHASES = {
  MENU: 'menu',
  LOADING: 'loading',
  RUN_OVERVIEW: 'run_overview',
  MOVE_MANAGER: 'move_manager',
  BATTLE: 'battle',
  POST_BATTLE: 'post_battle',
  VICTORY: 'victory'
};

export const initialState = {
  phase: PHASES.MENU,
  runConfig: null,
  hero: null,
  defeatedMonsters: [],
  currentMonsterIndex: null,
  lastBattleResult: null  // { won, learnedMoveId, xpGained, leveledUp, newLevel }
};

export function gameReducer(state, action) {
  switch (action.type) {

    case 'START_LOADING':
      return { ...state, phase: PHASES.LOADING };

    case 'CONFIG_LOADED': {
      const { config } = action;
      const base = config.hero.baseStats;
      return {
        ...state,
        phase: PHASES.RUN_OVERVIEW,
        runConfig: config,
        hero: {
          level: 1,
          xp: 0,
          maxHp: base.health,
          stats: { attack: base.attack, defense: base.defense, magic: base.magic },
          equippedMoves: [...config.hero.defaultMoves],
          learnedMoves: [...config.hero.defaultMoves]
        },
        defeatedMonsters: [],
        currentMonsterIndex: null,
        lastBattleResult: null
      };
    }

    case 'ENTER_BATTLE':
      return { ...state, phase: PHASES.BATTLE, currentMonsterIndex: action.monsterIndex };

    case 'BATTLE_END': {
      const { won, learnedMoveId, runComplete, xpGained, newXp, newLevel, leveledUp, newMaxHp, newStats } = action;
      let hero = { ...state.hero };
      let defeatedMonsters = [...state.defeatedMonsters];

      if (won) {
        const learnedMoves = learnedMoveId && !hero.learnedMoves.includes(learnedMoveId)
          ? [...hero.learnedMoves, learnedMoveId]
          : [...hero.learnedMoves];

        hero = {
          ...hero,
          level: newLevel,
          xp: newXp,
          maxHp: newMaxHp,
          stats: newStats,
          learnedMoves
        };

        if (!defeatedMonsters.includes(state.currentMonsterIndex)) {
          defeatedMonsters.push(state.currentMonsterIndex);
        }
      }

      const allDefeated = runComplete;

      return {
        ...state,
        phase: allDefeated ? PHASES.VICTORY : PHASES.POST_BATTLE,
        hero,
        defeatedMonsters,
        lastBattleResult: { won, learnedMoveId, xpGained, leveledUp, newLevel }
      };
    }

    case 'CONTINUE_FROM_POST_BATTLE':
      return { ...state, phase: PHASES.RUN_OVERVIEW, lastBattleResult: null };

    case 'OPEN_MOVE_MANAGER':
      return { ...state, phase: PHASES.MOVE_MANAGER };

    case 'CLOSE_MOVE_MANAGER':
      return { ...state, phase: PHASES.RUN_OVERVIEW };

    case 'EQUIP_MOVE': {
      const { moveId, slotIndex } = action;
      const equipped = [...state.hero.equippedMoves];
      equipped[slotIndex] = moveId;
      return { ...state, hero: { ...state.hero, equippedMoves: equipped } };
    }

    case 'RETURN_TO_MENU':
      return { ...initialState };

    default:
      return state;
  }
}
