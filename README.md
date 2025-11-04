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

## Atividades práticas

> [!NOTE]
>
> - As atividades serão realizadas com o GitHub Classroom e podem ser acessadas pelos links nas descrições das atividades.
> - No primeiro acesso, _**cada aluno deverá selecionar seu nome na lista para vincular sua conta no GitHub**_ e aceitar o convite para a atividade prática.
> - O repositório da atividade prática será criado automaticamente para cada aluno ou grupo (compartilhado entre os membros).
> - O aluno deverá clonar o repositório para seu computador, fazer as modificações necessárias e subir o repositório para o GitHub (`git push`).
> - Não é necessário nenhuma outra ação para submeter a atividade.
> - Nas atividades em grupo, ao acessar o link da atividade, o aluno deverá criar seu grupo ou ingressar no seu respectivo grupo se existir.

> [!IMPORTANT]
> _**Todos os membros dos grupos devem participar das atividades**_, registrando esta participação por meio da identificação dos commits com seus respectivos usuários no GitHub.

1. [INDIVIDUAL] Criar workflows para integração e implantação contínua para o projeto front-end utilizando o GitHub Actions.

    - O workflow de integração deve compilar o projeto e executar os testes (exceto E2E).
      - Comando para compilar o projeto: `ng build`
      - Comando para executar os testes: `ng test --browsers=ChromeHeadless --no-watch`
    - O workflow de implantação deve executar os testes E2E e fazer o _**deploy**_ para o ambiente de produção.
      - Comando para executar os testes E2E: `ng run sgcmapp:cypress-run`
      - Para executar os testes E2E, o back-end deve também estar em execução (com perfil `test`).
        - Comandos para iniciar o back-end:
          - `mvn clean package -Ptest -Dmaven.test.skip=true`
          - `nohup java -jar target/sgcmapi.jar &`
      - O _**job**_ de _**deploy**_ deve ser executado apenas se o _**job**_ do CI do front-end for executado com sucesso.
      - **IMPORTANTE**: No workflow de implantação, na etapa de autenticação no GHCR, deve ser usado o usuário e token de acesso pessoal de um dos membros do grupo, pois o repositório da atividade não conseguirá autenticar com as mesmas credenciais usadas no exemplo em sala de aula.
    - **ATENÇÃO**:
      - Para executar os comandos `ng` no GitHub Actions, é necessário configurar o Node.js e o Angular CLI no ambiente de execução, além de instalar as dependências do projeto.
      - Configurar a constante `API_URL` no arquivo `environment.ts` do projeto front-end.
      - Modificar as configurações de CORS no back-end para adicionar o host da aplicação front-end em produção.
      - A implantação deve ser feita obrigatoriamente por meio do GitHub Actions.
    - Link da atividade: <https://classroom.github.com/a/2a868ti->
    - Entrega: 10/11/2025 - 18:00h
