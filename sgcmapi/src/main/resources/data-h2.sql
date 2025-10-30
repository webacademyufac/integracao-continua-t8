SET REFERENTIAL_INTEGRITY FALSE;

INSERT INTO `atendimento` VALUES
    (DEFAULT,'2025-10-21','14:00:00','AGENDADO',1,1,1),
    (DEFAULT,'2025-10-21','14:00:00','AGENDADO',1,2,3),
    (DEFAULT,'2025-10-21','14:30:00','AGENDADO',2,3,4),
    (DEFAULT,'2025-10-21','15:00:00','AGENDADO',3,4,1),
    (DEFAULT,'2025-10-21','15:00:00','AGENDADO',1,5,5);

INSERT INTO `convenio` VALUES
    (DEFAULT,TRUE,'46.560.030/0001-53','contato@unimedriobranco.com.br','Unimed','Unimed Rio Branco Cooperativa de Trabalho Médico Ltda.','Thayline Figueredo Vaz','(68) 3668-1546'),
    (DEFAULT,TRUE,'29.309.127/0001-79','contato@amil.com.br','Amil','AMIL ASSISTENCIA MEDICA INTERNACIONAL S.A.','Davi Faria da Conceição','(11) 3279-3035'),
    (DEFAULT,TRUE,'92.693.118/0001-60','contato@bradescosaude.com.br','Bradesco','BRADESCO SAUDE S.A.','Leandra Paes dos Anjos','(21) 2503-0787');

INSERT INTO `especialidade` VALUES
    (DEFAULT,'Cardiologia'),
    (DEFAULT,'Dermatologia'),
    (DEFAULT,'Geriatria'),
    (DEFAULT,'Infectologia'),
    (DEFAULT,'Pediatria'),
    (DEFAULT,'Psiquiatria'),
    (DEFAULT,'Urologia');

INSERT INTO `paciente` VALUES
    (DEFAULT,'69903-134','Rio Branco','1945-09-28','giulia.bencatel@gmail.com','Rua Cedro, 100 - Parque dos Sabiás','AC','AB_POSITIVO','Giulia Farias Bencatel','F','(68) 98105-2583'),
    (DEFAULT,'69901-884','Rio Branco','1941-04-22','wallace.soriano@yahoo.com','Travessa Mossoró, 90 - Vitória','AC','B_POSITIVO','Wallace Macedo Inácio Soriano','M','(68) 97431-7722'),
    (DEFAULT,'69909-060','Rio Branco','1997-06-05','helen.vilar@gmail.com','Rua Adelaide, 50 - Rosa Linda','AC','AB_NEGATIVO','Helen Dutra Vilar','F','(68) 99752-4954'),
    (DEFAULT,'69911-262','Rio Branco','2009-03-10','mario.branco@uol.com.br','Rua Luiz Galvez, 200 - Conjunto Castelo Branco','AC','O_POSITIVO','Jean Schuenck Amorin','M','(68) 97120-8079'),
    (DEFAULT,'69980-970','Cruzeiro do Sul','1974-11-14','lucilene.lucas@gmail.com','Rua Rego Barros, 427 - Centro','AC','A_POSITIVO','Lucilene Santos Lucas','F','(68) 99384-3354');

INSERT INTO `profissional` VALUES
    (DEFAULT,'monique.nespoli@uol.com.br','Maria Adelia Serravalle Bezerra','CRM/AC 377','(68) 98205-4704',1,1),
    (DEFAULT,'elielson.andrade@gmail.com','Elielson Silveira Andrade','CRM/AC 455','(68) 98085-4910',1,1),
    (DEFAULT,'davi.mendes@yahoo.com','Davi Jesus Mendes','CRM/AC 123','(68) 98408-5352',4,3),
    (DEFAULT,'carla.valle@gmail.com','Carla da Paixão Valle','CRM/AC 234','(68) 98395-5604',3,1),
    (DEFAULT,'neuza.nobrega@uol.com.br','Neuza Biango Nobrega','CRM/AC 232','(68) 98561-6622',2,2);

INSERT INTO `unidade` VALUES
    (DEFAULT,'Rua Independência, 56 - Conjunto Bela Vista','Bela Vista'),
    (DEFAULT,'Rua Paulo VI, 100 - Bosque','Bosque'),
    (DEFAULT,'Rua Rui Barbosa, 252 - Centro','Cruzeiro do Sul');

INSERT INTO `usuario` VALUES
    (DEFAULT,TRUE,'Administrador','admin','ADMIN','admin'),
    (DEFAULT,TRUE,'Daniel','daniel','ADMIN','daniel'),
    (DEFAULT,TRUE,'Teste','teste','ATENDENTE','teste');

SET REFERENTIAL_INTEGRITY TRUE;
