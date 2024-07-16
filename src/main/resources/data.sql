INSERT INTO filrouge.users 

(firstname, lastname, email, user_password, grant_name, active)
VALUES  ('Pascal', 'KERWAN', 'p.kerwan@cryorbiter', '$2a$12$0ApZW/n/gtSYQFvFT.rJR.MQ7PmikEPT5NZAh8WNfpdVGUbkihAu.', 'RESPONSABLE', TRUE),
        ('Céline','VALENTIN', 'c.valentin@cryorbiter.com', '$2a$12$EK8Qj9F4418dedztDHGWWujiz1SNSYr/vimyVxdibOVKPk8Gz0hri','RESPONSABLE', TRUE),
        ('Nicolas', 'DURANT', 'n.durant@cryorbiter.com', '$2a$12$vFvqhryui3sH5D0ot7hI1.5jWcfXu9Zf29Tg3pQU.Qt/XB3CN9g/G', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Jerôme', 'LAVAL', 'g.laval@cryorbiter.com', '$2a$12$JLivf3krbQJR1RzkLE38fe660gY5yY4smuHicTnR9ZkuOkyGO.DDW', 'SERVICE_CLIENT', TRUE),
        ('Amandine', 'LAGRANGE', 'a.lagrange@cryorbiter.com', '$2a$12$2TQEy0C5sTpsO/BrMylWG.eHbXa2oPEW1FVdEteLrceS4xhU0oDvW', 'SERVICE_CLIENT', TRUE),
        ('Cindy', 'ROGER', 'c.roger@cryorbiter.com','$2a$12$WKOPWpwct19xMIMXN7BtA.9quP6nuooHH/5YVpYNKyL2Jjq5Vo.w6', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Charlène', 'ABIBI', 'a.abibi@cryorbiter.com','$2a$12$Zorhq.iJ5e9WyRZQ.fupIuAz7MV5zzUJQzr9faLIf7mIXo9uurhjK', 'SERVICE_CLIENT', TRUE),
        ('Damian', 'COUSIN', 'd.cousin@cryorbiter.com', '$2a$12$fPTTsGise2x7QCzbFu.tIO/v.kDwfhPKw9wf3kjMm2N.NL0k8CcrC', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Charlotte', 'BOULANGER', 'c.boulanger@cryorbiter.com', '$2a$12$z/jzve1Ol4i7EXwYaZ0iuecpdzPFwswbes3ziP14i39hSan0Z9Tl.', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Ivy', 'CHARLES', 'i.charles@cryorbiter.com', '$2a$12$CnqgiT/dKeElVaulcubWv.t9DjGoqfoRbdLVuOsQZD1sRxXnOZyk.', 'SERVICE_CLIENT', TRUE),
        ('Alizée', 'TETRAS', 'a.tetras@cryorbiter.com', '$2a$12$RffTlmPhUslPm1V59nfYgupvBupdrluB5KuRle6ONpaUAMmPjcyLu', 'SERVICE_CLIENT', TRUE),
        ('Léon', 'CARPENTIER', 'l.carpentier@cryorbiter.com', '$2a$12$zPQQMgNmBFl/jN.9cKmczeFiPlleESb6PGUocSqecSCfXWMQ5Tl52', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Gérard', 'PAUL', 'g.paul@cryorbiter.com', '$2a$12$FtFvmTvqMZS8yh2dPMuIyewKBVS0yAMYEEdaMNo88pQBAKcwkNmzm', 'GESTIONNAIRE_DES_VENTES', TRUE),
        ('Cédric', 'POULAIN', 'c.poulain@cryorbiter.com', '$2a$12$tPzrJj0NKvQz9FNcc1sKAOLyPoU46NFKK6Fi8PME8Cqcw/8MaCDZ.', 'SERVICE_CLIENT', TRUE),
        ('toto', 'TEST', 'resp@test', '$2a$12$h0fTzzH/67.xgUBy7lsOO.JSIXoIaBxi/4oMRz.n0ATii8aIobaa2', 'RESPONSABLE', TRUE),
        ('titi', 'TEST', 'serv@test', '$2a$12$h0fTzzH/67.xgUBy7lsOO.JSIXoIaBxi/4oMRz.n0ATii8aIobaa2', 'SERVICE_CLIENT', TRUE),
        ('tata', 'TEST', 'gest@test', '$2a$12$h0fTzzH/67.xgUBy7lsOO.JSIXoIaBxi/4oMRz.n0ATii8aIobaa2', 'GESTIONNAIRE_DES_VENTES', TRUE)
;
INSERT INTO filrouge.user_action (user_id, method, action_realisee, created_at)
VALUES 
    (1, 'Client', 'Consulté la liste des clients', CURRENT_TIMESTAMP),
    (2, 'Devis', 'Créé un nouveau contact', CURRENT_TIMESTAMP - INTERVAL '2 days'),
    (3, 'Order', 'Mis à jour les détails du client', CURRENT_TIMESTAMP - INTERVAL '5 days'),
    (1, 'User', 'Supprimé un enregistrement client', CURRENT_TIMESTAMP - INTERVAL '1 day');


INSERT INTO filrouge.customers
        (lastname, firstname, company, email, office_phone, mobile_phone, customer_status, guarantee, customer_comment, user_id)
VALUES  ('DAVOS', 'Dimitri', 'AVENTIS', 'd.davos@aventis.com', '0299874123', '0645464748', 'CLIENT', true, 'Ras', 1),
        ('JARDIN', 'Nicolas', 'START', 'n.jardin@starts.com', '0441123695', '0623258587', 'CLIENT', true, 'Envoi de cendre', 1),
        ('GAILLARD', 'Martine', 'SPORT3000', 'm.gaillard@sport3000.com', '0299887554', '0665231200', 'CLIENT', true, 'Préserver sa matière première', 2),
        ('MARTINEZ', 'Alban', 'LES DELICES', 'a.martinez@lesdelices.com', '0356698741', '0699215463', 'CLIENT', true, 'Préserver sa pâte', 3),
        ('PETIT', 'Mathieu', 'AU LAPIN CHASSEUR', 'm.petit@lapinchasseur.com', '0299852335', '0741523698', 'CLIENT', true, 'Conserver sa viande plus longtemps', 3),
        ('DUBOIS', 'Fabrice', 'BATENGEENER', 'f.dubois@batengeener.com', '0445523236', '0666311554', 'CLIENT', false, 'Souhaite envoyer son associé dans l espace', 3),
        ('LEROY', 'Nina', 'AU PRINTEMPS DE NINA', 'n.leroy@printempsdenina.com', '0390236584', '0745698213', 'CLIENT', true, 'Envoyer les cendres de sa grand-mère', 1),
        ('AUBERT', 'Leila', 'OPTIC1000', 'l.aubert@optic1000.com', '0512369545', '0613522456', 'INTERESSE', false, 'Hésite encore', null),
        ('GOURDIN', 'Stéphane', 'CAPITAL', 's.gourdin@capital.com', '0332521003', '0715853607', 'LEAD', false, 'doit confirmer avec son associé', null)
;
        
INSERT INTO filrouge.estimates
        (estimate_label, number_of_days, average_daily_rate, tva, estimate_status, estimate_type, estimate_comment, transfered, user_id, customer_id)
VALUES  ('Cryogénisation', '15', '105', '20', 'ARCHIVE', 'Service', 'Ceci est un commentaire', TRUE, 1, 1),
        ('Vol Spatial', '20', '250', '20', 'ARCHIVE', 'Transport', '', TRUE, 1, 2),
        ('Cryogénisation', '15', '105', '20', 'EN_COURS', 'Service', '', FALSE, 2, 3),
        ('Cryogénisation', '15', '105', '20', 'ARCHIVE', 'Service', '', TRUE, 2, 3),
        ('Cryogénisation', '15', '105', '20', 'VALIDE', 'Service', '', TRUE, 3, 4),
        ('Vol Spatial', '20', '250', '20', 'REFUSE', 'Transport', 'Ne veut pas de ce prix très attractif', TRUE, 3, 5),
        ('Vol Spatial', '20', '249', '20', 'EN_COURS', 'Transport', 'Nouvel essai', FALSE, 3, 5)
;  

INSERT INTO filrouge.orders
        (order_label, order_type, order_status, order_comment, estimate_id, user_id, customer_id)
VALUES  ('Cryogénisation', 'Commande', 'LIVREE', 'Cryogénisation effectuée', 1, 1, 1),
        ('Cryogénisation', 'Commande', 'EN_COURS_DE_LIVRAISON', 'Cryogénisation prévue dans 5 jours', 4, 2, 3),
        ('Transport', 'Commande', 'NON_LIVREE', 'Départ prévu dans 15 jours', 2, 1, 2)
;
 
