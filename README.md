# integracao-continua-t8

Repositório da disciplina Integração Contínua (Turma 8)

## Atualizando o repositório local

O código produzido em sala de aula, e compartilhado neste repositório, pode ser atualizado no repositório local com o comando:

```console
git pull
```

No entanto, se foram feitas alterações no repositório local, o comando acima pode gerar conflitos. Para evitar lidar com isso, é possível forçar uma atualização com o repositório remoto por meio dos comandos:

```console
git fetch origin
git reset --hard origin/main
```

O primeiro comando recebe as atualizações mais recentes do repositório remoto, e o segundo descarta todas as alterações locais e atualiza com o histórico mais recente do repositório remoto (branch main).

> [!IMPORTANT]
> As dependências do projeto front-end (/node_modules) não são compartilhadas no repositório. Para instalar as dependências, a partir da raiz do projeto (/sgcmapp), no prompt de comandos, digite: `npm install`.

## Sites de referência

- Software Delivery Guide (Martin Fowler): <https://martinfowler.com/delivery.html>
- GitHub Docs - Actions: <https://docs.github.com/pt/actions>
- Docker Docs: <https://docs.docker.com/get-started/overview/>
- Tutoriais:
  - [Criação de Token de Acesso Pessoal no GitHub](https://github.com/webacademyufac/tutoriais/blob/main/github-token/github-token.md)
  - [Configuração de Secrets no GitHub](https://github.com/webacademyufac/tutoriais/blob/main/github-secret/github-secret.md)
  - [Configuração do Dokploy](https://github.com/webacademyufac/tutoriais/blob/main/dokploy/dokploy.md)

## Ambiente de Desenvolvimento

> [!WARNING]
> A preparação adequada do ambiente de desenvolvimento é fundamental para o bom andamento das atividades da disciplina. Dedique atenção a esse passo e certifique-se de que o ambiente está corretamente configurado.

- [Preparação do Ambiente de Desenvolvimento Back-end](https://github.com/webacademyufac/tutoriais/blob/main/ambiente-desenvolvimento-backend.md)
- [Preparação do Ambiente de Desenvolvimento Front-end](https://github.com/webacademyufac/tutoriais/blob/main/ambiente-desenvolvimento-frontend.md)

## SGCM - Sistema de Gerenciamento de Clínica Médica

A demonstração de uso das ferramentas e tecnologias abordadas na capacitação é baseada em um projeto de exemplo, o SGCM. A documentação básica deste projeto está disponível [em outro repositório](https://github.com/webacademyufac/sgcmdocs) e aborda os seguintes tópicos:

- [Principais funcionalidades](https://github.com/webacademyufac/sgcmdocs#principais-funcionalides)
- [Histórias de usuário](https://github.com/webacademyufac/sgcmdocs#histórias-de-usuário)
- [Diagrama de Classes](https://github.com/webacademyufac/sgcmdocs#diagrama-de-classes)
- [Diagrama Entidade Relacionamento](https://github.com/webacademyufac/sgcmdocs#diagrama-entidade-relacionamento)
