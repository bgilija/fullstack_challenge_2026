# The Hateful Five

A turn-based RPG where a Knight fights through a gauntlet of 5 monsters. Beat them all, steal their moves, survive.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21 · Spring Boot 3.2 · Maven |
| Frontend | React 18 · Vite · Plain CSS |

---

## How to Run

**Backend** (port 8080)
```bash
cd backend-java
mvn spring-boot:run
```

**Frontend** (port 8082)
```bash
cd frontend
npm install   # first time only
npm run dev
```

Open **http://localhost:8082** in your browser.

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/config` | Returns the full run config — all 5 monsters, all 24 moves, hero base stats and XP thresholds. Called once at run start. |
| POST | `/api/battle/turn` | Resolves a full battle turn server-side. Receives hero's chosen move + full battle state, returns HP changes, buff updates, log lines and winner. |
| GET | `/api/monster-move` | Standalone monster AI endpoint. Given battle state, returns the move the monster would play. |

---

## Project Structure

```
fullstack_challenge/
├── backend-java/          Spring Boot backend
│   └── src/main/java/backendApp/
│       ├── controller/    REST endpoints
│       ├── transferObjects/ DTOs sent to/from frontend
│       ├── effect/        Domain effect hierarchy (DamageEffect, BuffSelfEffect, ...)
│       ├── move/          AbstractMove + 24 concrete move classes
│       ├── monster/       AbstractMonster + 5 concrete monster classes
│       └── service/       CombatService · MonsterAiService · GameDataService
│
└── frontend/              React + Vite frontend
    └── src/
        ├── components/    MainMenu · RunMap · BattleScreen · PostBattle · MoveManager · VictoryScreen
        ├── api.js         fetch wrappers (fetchConfig, postBattleTurn)
        ├── combat.js      Display helpers (getMoveType, computeHeroStats, xpToLevel)
        ├── gameReducer.js Game state machine (phases, hero progression)
        └── index.css      All styles — pixel art theme using Press Start 2P font
```

---

## Game Systems

**Stats** — Every character has Health, Attack, Defense, Magic.

**Damage formulas**
- Physical: `max(1, floor(effAtk × baseValue/100 − effDef × 0.3))`
- Magic: `max(1, floor(effMag × baseValue/100))` — bypasses Defense

**Buffs / Debuffs** — Temporary percentage modifiers on any stat, lasting N turns.

**Progression** — Hero starts at Level 1. Each win awards XP; enough XP triggers a level-up that raises all stats. After beating a monster the hero learns one of its moves at random and can equip it before the next fight (max 4 moves equipped at a time).

**Monster order** — Goblin Warrior → Giant Spider → Witch → Goblin Mage → Dragon
