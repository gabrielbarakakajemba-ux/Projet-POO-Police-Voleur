# Projet-POO-Police-Voleur

🎮 Projet : Jeu de Stratégie "Police vs Voleur"

-> Ce projet est une application desktop développée en Java dans le cadre de ma 2e année d'Informatique Appliquée. Il met en œuvre une architecture structurée alliant interface graphique, logique de jeu et persistance de données.

Description du Projet

-> Le jeu oppose deux camps sur un plateau circulaire spécifique (5 nœuds interconnectés). L'objectif est simple mais stratégique : les Policiers (P) doivent encercler le Voleur (V) pour l'immobiliser, tandis que le voleur doit manœuvrer pour éviter la capture.

Stack Technique
	Langage : Java (JDK 17+)

	Interface Graphique : Swing / AWT (conception personnalisée du plateau)

	Gestionnaire de projet : Maven

	Base de données : PostgreSQL (PG Admin) pour la gestion des joueurs et l'historique des parties.

	Architecture : Pattern DAO (Data Access Object) pour isoler la logique métier de l'accès aux données.

Fonctionnalités implémentées

	Connexion sécurisée à une base de données PostgreSQL.
	CRUD complet pour la gestion des profils joueurs.
	Plateau de jeu dynamique respectant les contraintes géométriques du sujet.
	Suivi en temps réel des scores et des résultats de parties.
