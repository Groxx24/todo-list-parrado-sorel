# Our Lists

Shared to-do lists for two people. Built with Compose Multiplatform, Android target only for now.
Single Gradle module `:app`, package `com.parradosorel.todo` (the applicationId is
`como.todo.parradosorel`, the id the Firebase app is registered under).

## Architecture

Same rules as the other apps here: SOLID, MVVM, Clean Architecture.

```
UI (Compose)  →  ViewModel  →  Use case  →  Repository interface (domain)  ←  data implementation
```

Dependencies point inward only: `ui` → `domain` ← `data`. `domain` is plain Kotlin. Shared code
goes in `app/src/commonMain` and must not use `java.*` or `android.*`; `androidMain` holds only
`MainActivity`, the application class, the Room database builder, the manifest and launcher
resources. Dependencies are wired by hand in `di/AppContainer.kt` and nowhere else.

## Screens

- `ui/lists/`: the entry point. Starts empty ("No lists yet") and shows every list, oldest first,
  with a "New list" button.
- `ui/newlist/`: a name (trimmed, 1 to `MAX_LIST_NAME_LENGTH` = 40 characters) and one of 48 icons
  in a 6-wide grid; Save is enabled once the name is not blank. Back drops what was typed.
- Navigation is `rememberSaveable` state in `ui/App.kt`.
- Colours live in `ui/theme/Theme.kt` (teal, coral accent, light and dark); the window background
  in `androidMain/res/values{,-night}/colors.xml` matches each scheme's `background`.
- Strings are in `composeResources/values/strings.xml`, English only for now.

## Data

- `TodoList` is an `id` (random UUID, so lists can later sync between phones without clashes),
  `name`, `icon` and `createdAt`.
- `ListIcon` is the 48 icons, in picker order. It is saved by name, so never rename an entry; an
  unknown name reads as `TODO`. The emoji and screen-reader label of each live only in
  `ui/icons/ListIcons.kt`.
- Lists are stored on the phone in Room (`todo.db`, table `lists`) through `data/local/RoomTodoLists`.
  It is the only copy, so schema changes need a migration; the schema is exported to `app/schemas/`.
- Not shared yet. Sharing means a Firestore `TodoListRepository` swapped in `AppContainer`; nothing
  above the data layer changes.
- Firebase is set up but not used by any code yet: the `com.google.gms.google-services` plugin reads
  `app/google-services.json` (gitignored, so every checkout needs its own copy), and Firestore and
  Auth come through GitLive's multiplatform SDK (`dev.gitlive:firebase-*`) so the Firebase code can
  live in `commonMain`. The Firebase BoM in `androidMain` is pinned to the version that GitLive
  release is built against, which is also why the app targets JVM 17.

## Build

Use Android Studio's bundled JDK; the system default is too new for this Gradle version:

```sh
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```
