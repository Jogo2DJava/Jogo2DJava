# Guia de Apresentação Oral — Flappy Bird em Java 2D (POO)

## 1. Discurso de Abertura (Pitch de 30 segundos)

> "Professor, nosso projeto é uma recriação completa do jogo **Flappy Bird em Java 2D**, desenvolvida do zero sem usar imagens prontas — todo o visual do pássaro, dos canos e do chão em movimento é desenhado via código. Nós estruturamos o sistema dividindo as responsabilidades em pacotes claros: temos o pacote `entities` para os objetos físicos do mundo do jogo, o pacote `game` que controla as regras da partida, pontuação e colisões, e o pacote `ui` que cuida das telas. Tudo foi pensado para aplicar na prática os quatro pilares da Programação Orientada a Objetos: **Abstração, Herança, Encapsulamento e Polimorfismo**."

---

## ⭐ ROTEIRO ESPECIAL: A Sua Parte (Abstração e Polimorfismo — Fala + Onde Apontar no Código)

Este roteiro foi desenhado para você **ler ou falar enquanto clica nos arquivos e aponta para as linhas exatas na tela**. Dica de ouro: antes de começar a apresentação, já deixe esses **5 arquivos abertos nas abas** da sua IDE na ordem abaixo!

### Ordem das abas abertas na IDE:
1. `GameObject.java` (pacote `flappybird.entities`)
2. `Screen.java` (pacote `flappybird.ui`)
3. `Bird.java` (pacote `flappybird.entities`)
4. `Pipe.java` (pacote `flappybird.entities`)
5. `Game.java` (pacote `flappybird.game`)

---

### PARTE 1: Apresentando a ABSTRAÇÃO (2 exemplos no código)

#### Passo 1.1 — Classe Abstrata `GameObject.java`
* **🖱️ O que fazer na tela:** Clique na aba do arquivo `GameObject.java` (que fica na pasta `src/main/java/flappybird/entities/GameObject.java`).
* **👉 Onde apontar com o mouse:**
  * Primeiro aponte para a **linha 14**: `public abstract class GameObject`
  * Depois desça até as **linhas 49 e 56**: `public abstract void update();` e `public abstract void draw(Graphics2D g);`
* **💻 O que você vai ver na tela:**
  ```java
  // Linha 14:
  public abstract class GameObject {
  ...
  // Linha 49:
  public abstract void update();

  // Linha 56:
  public abstract void draw(Graphics2D g);
  ```
* **🗣️ O que falar para o professor (citando onde está):**
  > *"Professor, eu vou explicar e mostrar no código como nós aplicamos a **Abstração** e o **Polimorfismo**.*
  > *Começando pela **Abstração**, nós usamos ela de duas formas: com Classe Abstrata e com Interface.*
  > *Aqui no pacote `entities`, dentro do arquivo **`GameObject.java`**, na **linha 14**, nós declaramos `public abstract class GameObject`. Ela funciona como um molde: não faz sentido existir um 'objeto genérico' solto no jogo, só fazem sentido objetos reais como o pássaro, o cano ou o chão.*
  > *Descendo aqui para a **linha 49** e para a **linha 56**, nós criamos dois métodos abstratos sem corpo: `public abstract void update()` e `public abstract void draw(Graphics2D g)`.Repare que eles terminam com ponto e vírgula e não têm chaves `{}`. Isso é um contrato obrigatório: qualquer classe que herdar de `GameObject` é obrigada a programar como ela se move no `update()` e como ela é desenhada na tela no `draw()`."*

---

#### Passo 1.2 — Interface `Screen.java`
* **🖱️ O que fazer na tela:** Clique na aba do arquivo `Screen.java` (que fica na pasta `src/main/java/flappybird/ui/Screen.java`).
* **👉 Onde apontar com o mouse:**
  * Aponte para a **linha 16** (`public interface Screen`) e para a **linha 24** (`void draw(Graphics2D g, Game game);`).
* **💻 O que você vai ver na tela:**
  ```java
  // Linha 16:
  public interface Screen {
      // Linha 24:
      void draw(Graphics2D g, Game game);
  }
  ```
* **🗣️ O que falar para o professor (citando onde está):**
  > *"A segunda forma de **Abstração** está aqui no pacote `ui`, no arquivo **`Screen.java`**. Na **linha 16**, nós criamos a interface `public interface Screen`, e na **linha 24** definimos a assinatura `void draw(Graphics2D g, Game game)`. Com essa abstração, o painel do jogo sabe que toda tela — seja o Menu Inicial (`MenuScreen`), o Placar durante o jogo (`HudScreen`) ou a tela de Fim de Jogo (`GameOverScreen`) — segue exatamente esse mesmo contrato para ser desenhada."*

---

### PARTE 2: Apresentando o POLIMORFISMO (3 exemplos no código)

#### Passo 2.1 — `@Override` de `update()` comparando `Bird.java` com `Pipe.java`
* **🖱️ O que fazer na tela:** Clique na aba do arquivo `Bird.java` (`src/main/java/flappybird/entities/Bird.java`) na **linha 62**, e depois alterne para `Pipe.java` (`src/main/java/flappybird/entities/Pipe.java`) na **linha 42**.
* **👉 Onde apontar com o mouse:**
  * Em `Bird.java`, aponte para as **linhas 62 a 76** (`@Override public void update()`).
  * Em `Pipe.java`, aponte para as **linhas 42 a 46** (`@Override public void update()`).
* **💻 O que você vai ver na tela:**
  ```java
  // Em Bird.java (Linhas 62 a 76):
  @Override
  public void update() {
      velocityY += GameConfig.GRAVITY; // Linha 65
      if (velocityY > GameConfig.MAX_FALL_SPEED) {
          velocityY = GameConfig.MAX_FALL_SPEED;
      }
      moveBy(0, velocityY); // Linha 73
      wingTick++;
  }

  // Em Pipe.java (Linhas 42 a 46):
  @Override
  public void update() {
      moveBy(-GameConfig.PIPE_SPEED, 0); // Linha 45
  }
  ```
* **🗣️ O que falar para o professor (citando onde está):**
  > *"Agora passando para o **Polimorfismo** — que significa 'muitas formas', ou seja, objetos diferentes responderem ao mesmo comando cada um do seu próprio jeito.*
  > *Abrindo aqui o arquivo **`Bird.java`**, na **linha 62**, temos a anotação `@Override` (pronuncia-se 'ôver-ráid') sobrescrevendo o método `update()`. Veja que na **linha 65** o pássaro soma a gravidade (`velocityY += GameConfig.GRAVITY`) e na **linha 73** ele se move na vertical com `moveBy(0, velocityY)`.*
  > *Já se eu abrir o arquivo **`Pipe.java`**, aqui na **linha 42**, nós temos exatamente o mesmo método `@Override public void update()`, só que na **linha 45** a implementação dele faz `moveBy(-GameConfig.PIPE_SPEED, 0)`, movendo o cano na horizontal para a esquerda. Ou seja: a chamada `update()` é a mesma, mas o comportamento muda de acordo com o objeto."*

---

#### Passo 2.2 — Polimorfismo na Caixa de Colisão (`getBounds()`) em `Bird.java`
* **🖱️ O que fazer na tela:** Volte para a aba `Bird.java` e aponte logo abaixo do `update()`, nas **linhas 83 a 93**.
* **👉 Onde apontar com o mouse:**
  * Aponte para `@Override public Rectangle getBounds()` na **linha 83** e para `int insetX = 3; int insetY = 3;` nas **linhas 85 e 86**.
* **💻 O que você vai ver na tela:**
  ```java
  // Em Bird.java (Linhas 83 a 93):
  @Override
  public Rectangle getBounds() {
      int insetX = 3;
      int insetY = 3;
      return new Rectangle(
              (int) Math.round(getX()) + insetX,
              (int) Math.round(getY()) + insetY,
              getWidth() - (insetX * 2),
              getHeight() - (insetY * 2)
      );
  }
  ```
* **🗣️ O que falar para o professor (citando onde está):**
  > *"Um segundo ponto muito legal de **Polimorfismo** está logo aqui embaixo, ainda em **`Bird.java`**, nas **linhas 83 a 93**. Lá na classe mãe `GameObject` (na linha 63), o método `getBounds()` devolve um retângulo do tamanho exato do objeto para calcular colisões. Os canos e o chão usam esse comportamento padrão. Mas aqui no `Bird`, na **linha 83**, nós sobrescrevemos o `getBounds()` com `@Override` e criamos uma margem `insetX = 3` e `insetY = 3` (nas **linhas 85 e 86**), deixando a caixa de colisão do pássaro 3 pixels menor de cada lado. Quando o detector de colisão chama `bird.getBounds()`, o Polimorfismo entrega essa caixa reduzida automaticamente, deixando o jogo mais justo quando o pássaro passa raspando no cano."*

---

#### Passo 2.3 — Coleção Polimórfica (`List<GameObject>`) em `Game.java`
* **🖱️ O que fazer na tela:** Clique na aba `Game.java` (`src/main/java/flappybird/game/Game.java`) e role até a **linha 169**.
* **👉 Onde apontar com o mouse:**
  * Aponte para as **linhas 169 a 178** (`public List<GameObject> getGameObjects()`).
* **💻 O que você vai ver na tela:**
  ```java
  // Em Game.java (Linhas 169 a 178):
  public List<GameObject> getGameObjects() {
      List<GameObject> objects = new ArrayList<>();
      objects.add(bird);
      objects.add(ground);
      for (PipePair pair : pipeManager.getPipePairs()) {
          objects.add(pair.getTopPipe());
          objects.add(pair.getBottomPipe());
      }
      return Collections.unmodifiableList(objects);
  }
  ```
* **🗣️ O que falar para o professor (citando onde está):**
  > *"Por fim, para fechar o **Polimorfismo**, aqui no pacote `game`, dentro do arquivo **`Game.java`**, nas **linhas 169 a 178**, temos o método `getGameObjects()`. Na **linha 170**, nós criamos uma lista do tipo genérico da classe mãe: `List<GameObject> objects`. E nas **linhas 171 a 175**, nós colocamos dentro dessa mesma lista objetos de classes diferentes: o `bird` (linha 171), o `ground` (linha 172) e os canos `topPipe` e `bottomPipe` (linhas 174 e 175). Graças ao Polimorfismo, o Java trata todos eles de forma uniforme como `GameObject`."*

---

## 2. Mapa Geral dos 4 Critérios Avaliativos (Resumo da Equipe)

### 🔒 1. Encapsulamento
* **O Conceito em 1 Frase (Analogia):** É como o cofre de um placar eletrônico ou o motor fechado de um carro: ninguém de fora pode colocar a mão direto nas engrenagens para adulterar os pontos ou teletransportar o pássaro; você só interage usando os botões permitidos.
* **Onde está no NOSSO código:**
  * **No placar (`src/main/java/flappybird/game/ScoreManager.java`, linhas 11–45):** Os atributos `private int score` (linha 13) e `private int bestScore` (linha 14) são privados (`private`). Não existe um método `setScore(999)`. A única forma de somar pontos é pelo método público `increment()` (linha 24), que soma de 1 em 1 e já atualiza o recorde automaticamente.
  * **No pássaro e na base (`src/main/java/flappybird/entities/GameObject.java`, linhas 16–27 e `src/main/java/flappybird/entities/Bird.java`, linhas 27–51):** As coordenadas `private double x` e `private double y` em `GameObject` e a velocidade de queda `private double velocityY` em `Bird` (linha 27) são privadas. Para o pássaro voar na tela, o jogo chama o método público `flap()` (linha 49 de `Bird.java`), que aplica a força de pulo configurada.
  * **Proteção de listas (`src/main/java/flappybird/game/PipeManager.java`, linhas 104–106 e `src/main/java/flappybird/game/Game.java`, linhas 169–178):** Os métodos `getPipePairs()` e `getGameObjects()` usam `Collections.unmodifiableList(...)`, entregando uma lista "somente leitura" para ninguém apagar canos ou objetos por acidente.
* **O que falar para o professor (Script pronto):**
  > *"Professor, aplicamos o **Encapsulamento** protegendo os atributos com o modificador `private` (pronuncia-se 'práivet'). Um ótimo exemplo está aqui na classe `ScoreManager`: o `score` e o `bestScore` são privados e não têm 'setters' livres. O placar que aparece na tela só pode subir de 1 em 1 através do método `increment()`, impedindo que qualquer outra parte do código adultere a pontuação. Além disso, em `PipeManager`, retornamos a lista de canos com `Collections.unmodifiableList`, aplicando encapsulamento defensivo."*

### 🧬 2. Herança
* **O Conceito em 1 Frase (Analogia):** É como o "DNA de família": em vez de programar do zero que o pássaro tem posição `(x, y)` e tamanho, que o cano também tem `(x, y)` e tamanho, e o chão também, criamos um "pai" com essas características e todos os filhos herdam isso prontinho.
* **Onde está no NOSSO código:**
  * Palavra-chave `extends` (pronuncia-se *ékstends*) nas três classes filhas do pacote `entities` que herdam da classe mãe `GameObject` (`src/main/java/flappybird/entities/GameObject.java`, linha 14):
    1. `src/main/java/flappybird/entities/Bird.java` (linha 25): `public class Bird extends GameObject` (usa `super(...)` na linha 39 e herda `moveBy`, `setPosition`, `getX()`, `getY()`).
    2. `src/main/java/flappybird/entities/Pipe.java` (linha 15): `public class Pipe extends GameObject` (usa `super(...)` na linha 38).
    3. `src/main/java/flappybird/entities/Ground.java` (linha 15): `public class Ground extends GameObject` (usa `super(...)` na linha 29).
* **O que falar para o professor (Script pronto):**
  > *"Para demonstrar a **Herança**, criamos a superclasse `GameObject` que concentra tudo o que qualquer elemento visual do jogo tem em comum: posição `x`, `y`, largura `width`, altura `height` e o cálculo da caixa de colisão `getBounds()`. Aí as classes `Bird`, `Pipe` e `Ground` usam a palavra-chave `extends GameObject` e acionam o construtor `super()` para reaproveitar toda essa estrutura sem repetir código."*

### 📐 3. Abstração
* **O Conceito em 1 Frase (Analogia):** É como um "molde" ou "contrato de trabalho": sabemos que todo objeto do jogo precisa *se atualizar* e *se desenhar na tela*, mas o conceito genérico de "Objeto do Jogo" não existe sozinho na tela — só existem os objetos reais (pássaro, cano, chão) que seguem esse contrato.
* **Onde está no NOSSO código:**
  * **Classe Abstrata (`src/main/java/flappybird/entities/GameObject.java`, linhas 14, 49 e 56):** `public abstract class GameObject`, `public abstract void update();` e `public abstract void draw(Graphics2D g);`.
  * **Interface de Telas (`src/main/java/flappybird/ui/Screen.java`, linhas 16 e 24):** `public interface Screen` com `void draw(Graphics2D g, Game game);`.

### 🎭 4. Polimorfismo
* **O Conceito em 1 Frase (Analogia):** É dar a **mesma ordem** para objetos diferentes e cada um obedecer do seu próprio jeito: quando o jogo grita *"Atualizar (`update`)!"*, o pássaro cai com a gravidade, o cano anda para a esquerda e o chão gira as listras da grama.
* **Onde está no NOSSO código:**
  * **Sobrescrita (`@Override`) de `update()`:** `Bird.java` (linha 62), `Pipe.java` (linha 42) e `Ground.java` (linha 34).
  * **Sobrescrita (`@Override`) de `getBounds()`:** `Bird.java` (linhas 83–93).
  * **Coleção Polimórfica (`List<GameObject>`):** `Game.java` (linhas 169–178).
  * **Renderização Polimórfica de Telas (`Screen`):** `GamePanel.java` (linhas 27–29 e 105–111).

---

## 3. Simulado: Perguntas Difíceis do Professor (Focadas em Abstração e Polimorfismo)

### Pergunta 1 (Sobre Abstração): *"Por que vocês criaram `GameObject` como uma Classe Abstrata (`abstract class`) e `Screen` como uma Interface (`interface`)? Qual a diferença entre as duas escolhas aqui no projeto?"*
* **Onde isso está:** `GameObject.java` (linha 14) vs. `Screen.java` (linha 16)
* **O que responder com segurança:**
  > *"Professor, nós usamos **classe abstrata** no `GameObject` porque, além do contrato dos métodos `update()` e `draw()`, os objetos do mundo físico precisam compartilhar **estado (atributos)** — todo mundo tem `x`, `y`, `width` e `height` guardados lá dentro (linhas 17 a 26). Já nas telas (`Screen.java`, linha 16), usamos **interface** porque `MenuScreen`, `HudScreen` e `GameOverScreen` não têm coordenadas físicas em comum; elas só precisam assinar o mesmo contrato de comportamento, que é o método `draw(Graphics2D g, Game game)` na linha 24."*

### Pergunta 2 (Sobre Polimorfismo): *"O que aconteceria se vocês apagassem o método `getBounds()` da linha 83 da classe `Bird`? O código daria erro de compilação?"*
* **Onde isso está:** `Bird.java` (linha 83) e `GameObject.java` (linha 63)
* **O que responder com segurança:**
  > *"Não daria erro de compilação, professor! Porque a classe mãe `GameObject` já tem um `getBounds()` padrão implementado na **linha 63**. Se a gente apagasse o `@Override` do `Bird`, ele simplesmente usaria o retângulo completo da classe mãe. Nós sobrescrevemos polimorficamente na **linha 83 do `Bird`** apenas para diminuir 3 pixels de cada lado e deixar a colisão mais justa para o jogador."*

### Pergunta 3 (Sobre Herança vs. Composição): *"Eu vi que `Bird`, `Pipe` e `Ground` herdam de `GameObject`. Por que a classe `PipePair` (par de canos) também está no pacote `entities`, mas NÃO tem `extends GameObject`?"*
* **Onde isso está:** `src/main/java/flappybird/entities/PipePair.java` (linhas 16–20)
* **O que responder com segurança:**
  > *"Professor, nós fizemos isso de propósito para aplicar **Composição** em vez de Herança. Um `GameObject` representa um objeto único que tem uma única caixa de colisão retangular (`getBounds()`). Já o `PipePair` tem um buraco no meio onde o pássaro passa para pontuar. Se ele herdasse de `GameObject`, a caixa de colisão dele fecharia o vão inteiro e o pássaro bateria numa parede invisível. Por isso, o `PipePair` é **composto** por dois objetos `Pipe` separados (`topPipe` na linha 18 e `bottomPipe` na linha 19)."*
