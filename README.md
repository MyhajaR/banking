# Bank account kata

Fonctionnalités réalisées: 
- dépot d'argent dans le compte
- retrait
- Relevé de compte

Par souci de scalabilité, une architecture hexagonale a été adoptée.

## Architecture
- **domain** contient le code métier (gestion des dépots, retraits et relevé de compte)
- **application** fera office de port ( interface qui lie le code métier à l'exterieur)
- **adapter** regroupe le code externe 
  - api pour les appels entrants (input)
  - persistence pour les appels sortants (output), dans notre cas la relation avec la base de donnée 
