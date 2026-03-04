# AndroidTest — Claro App

Aplicativo Android de demonstração desenvolvido com Jetpack Compose, Clean Architecture e Koin, simulando as telas de **Meus Dados** e **Meu Plano** para dois tipos de plataforma: **NP** (nova) e **RW** (antiga).

---

## Requisitos

- `compileSdk 36` / `minSdk 28`
- `MVVM`
- `Kotlin`
- `Koin`
- `Coil`
- `Testes unitários`

---

## Stack

| | |
|---|---|
| UI | Jetpack Compose |
| Navegação | Navigation Compose |
| DI | Koin |
| Rede | Retrofit + OkHttp |
| Serialização | Gson |
| Imagens | Coil |
| Testes | JUnit4 + Coroutines Test |

---

## Estrutura

```
data/
  mapper/       # DTO → Domain
  model/        # DTOs com @SerializedName
  remote/       # RetrofitProvider + AssetInterceptor
  repository/   # NP e RW para cada feature
  service/      # ApiService (interface Retrofit)

domain/
  model/        # Modelos de domínio
  usecase/      # GetMyDataUseCase / GetMyPlanUseCase

presentation/
  feature/
    home/
    mydata/
      mapper/   # Domain → UiModel
    myplan/
      mapper/   # Domain → UiModel
  navigation/
  components/
```

---

## Testes

Cada camada é testada de forma isolada usando fakes manuais, sem frameworks de mock.

```
factory/      # DataFactory — DTOs e domain models reutilizáveis
repository/   # Verifica o mapeamento DTO → Domain
usecase/      # Verifica a delegação ao repositório
viewmodel/    # Verifica estados (Loading → Success / Error) e efeitos
```
---

