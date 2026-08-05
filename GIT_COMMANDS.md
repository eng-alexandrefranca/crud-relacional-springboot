# 🚀 Guia de Comandos Principais do Git

Este guia reúne os comandos essenciais do Git para gerenciamento de repositórios, desde a inicialização até o fluxo diário de trabalho.

---

## 🔄 Fluxo de Trabalho Diário (Essenciais)

Estes são os comandos que você usará repetidamente enquanto desenvolve. **Dica:** Sempre faça um `pull` antes de começar a trabalhar para evitar conflitos!

```bash
# 📥 Atualiza seu repositório local com as últimas mudanças do servidor remoto
git pull origin main

# 👀 Verifica o status dos arquivos (modificados, não monitorados, etc.)
git status

# ➕ Adiciona um arquivo específico para o commit
git add README.md

# ➕ Adiciona TODOS os arquivos modificados e novos de uma vez
git add .

# 💾 Grava as alterações salvas no histórico local
git commit -m "init"

# 📤 Envia as alterações da branch atual para o servidor remoto
git push origin main
```

---

## 📤 Enviar para um Repositório GitHub Existente

Se você já tem um projeto Git local e quer apenas vinculá-lo ao GitHub:

```bash
# Inicia o Git na sua pasta local
git init

# Adiciona todos os arquivos locais para o próximo envio
git add .

# Salva a versão atual dos arquivos localmente
git commit -m "Primeiro commit local"

# Vincula o repositório local ao remoto
git remote add origin https://github.com/[meu-repositorio].git

# Garante que a branch principal se chama 'main'
git branch -M main

# Sincroniza o GitHub com o local (necessário pois o GitHub possui histórico independente)
git pull origin main --allow-unrelated-histories

# Envia os arquivos para o repositório remoto
git push -u origin main
```

---

## 🆕 Criar um Novo Repositório Local (Linha de Comando)

Use estes comandos para iniciar um projeto do zero localmente e enviá-lo ao GitHub:

```bash
# Cria um arquivo README.md com o título do projeto
echo "# crud-relacional-springboot" >> README.md

# Inicializa o repositório Git local
git init

# Adiciona o arquivo README ao palco (staging area)
git add README.md

# Salva as alterações localmente com uma mensagem
git commit -m "first commit"

# Define a branch principal como 'main'
git branch -M main

# Vincula o repositório local ao repositório remoto no GitHub
git remote add origin https://github.com/[meu-repositorio].git

# Envia o código para o GitHub (e define a branch padrão com -u)
git push -u origin main
```

---

## 🌿 Gerenciamento de Branches (Ramificações)

```bash
# Lista todas as branches locais (a atual terá um asterisco)
git branch

# Cria uma nova branch
git branch <nome-da-branch>

# Muda para a branch especificada
git checkout <nome-da-branch>

# Atalho: Cria e muda para a nova branch ao mesmo tempo
git checkout -b <nome-da-branch>

# Junta as alterações da branch especificada na branch atual
git merge <nome-da-branch>
```

### 💡 Exemplo Prático: Entendendo o `git merge`

O `git merge` serve para unir o histórico e o código de duas branches diferentes. 

**Cenário:** Você criou uma branch chamada `nova-funcao` para criar uma tela de login. O código está pronto e testado, e agora você quer trazer esse trabalho para a branch principal (`main`).

1. Mude para a branch que vai **receber** as atualizações:
   ```bash
   git checkout main
   ```
2. Execute o merge trazendo as modificações da branch secundária:
   ```bash
   git merge nova-funcao
   ```

#### 🎨 Representação Visual

**Antes do Merge:**
A branch `main` não conhece as modificações feitas na `nova-funcao`.
```text
main         o---o
                  \
nova-funcao        o---o (Código do login está aqui)
```

**Depois do `git merge nova-funcao`:**
O Git une as duas linhas do tempo. A branch `main` agora possui o código e o histórico da tela de login.
```text
main         o---o-------● (Inclusão do login feita com sucesso!)
                  \     /
nova-funcao        o---o
```

---

## ➕ Comandos Adicionais Importantes

Para tornar seu fluxo de trabalho mais completo, utilize estes comandos extras no dia a dia:

### 📥 Clonar e Inspecionar Remotos

```bash
# Baixa um repositório existente do GitHub para o seu computador
git clone <URL_DO_REPOSITORIO>

# Exibe as URLs dos repositórios remotos vinculados
git remote -v

# Baixa o histórico do servidor remoto, mas não mescla com seu código local
git fetch
```

### 📜 Histórico e Inspeção

```bash
# Mostra o histórico de commits do projeto
git log

# Mostra o histórico de forma simplificada (uma linha por commit)
git log --oneline

# Mostra as alterações exatas feitas nos arquivos modificados
git diff
```

### 🧹 Desfazer Alterações e Salvar Trabalho

```bash
# Remove um arquivo do palco (staging), mas mantém as alterações dele
git restore --staged <nome-do-arquivo>

# Descarta as alterações locais de um arquivo (volta ao último commit)
git restore <nome-do-arquivo>

# Altera a mensagem do último commit realizado (caso tenha digitado errado)
git commit --amend -m "Nova mensagem corrigida"

# Salva suas modificações atuais temporariamente e limpa o diretório
git stash

# Recupera as alterações que foram salvas temporariamente com o stash
git stash pop
```
