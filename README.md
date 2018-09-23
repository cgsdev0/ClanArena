# Clan Arena

## Planned Features

### Battles

Any member of a team can challenge another team to a clan battle at any time. The number of players from each team is specified by the challenger. If there are enough players on each team, the challenge is sent. If the challenged team accepts, players have a short window to register for the battle. When enough players are registered, all involved players are warped to the arena and given a kit.

### Kits

A kit is a set of items specified by an admin, which can be selected by a challenger for a clan battle. When the battle starts, each involved player will be given the kit. Their existing inventory (including XP, armor, etc) should be automatically stored safely for the duration of the fight, and returned after.

### Arenas

Arenas are areas where players can hold a clan battle. There can be multiple arenas, but only one match can be in progress at a time in an arena.

### Integrations

* **McMMO** - disable parties during fight
* **SimpleClans** - used as a 'team' implementation
* **Factions** - alternative 'team' implementation
* **Vault** - allow for wagers on matches

## Notes:

* Use `adapters` for team abstraction (SimpleClans, Factions, etc.)
* Add `mcmmo.party.friendlyfire` permission during battle
* Potentially disable mcmmo perks during battle
