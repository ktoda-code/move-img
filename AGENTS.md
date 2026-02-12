# MoveImg – AGENTS.md

This repo is a Kotlin Multiplatform project, desktop-first (Windows initially).
The app is an image viewer + slideshow (NOT an image editor).

## 1) Goals & Product Scope

- Core: load images from folders, browse, and run a slideshow.
- UX: minimal, fast, beautiful. Keyboard-friendly. Drag and Drop.
- Out of scope: image editing tools, filters, drawing, heavy asset catalogs.
- Performance: Fast, caching, layered, minimal, low not object heavy.

## 2) Tech Stack

- Kotlin Multiplatform (KMP)
- Desktop UI: Jetpack Compose Multiplatform (preferred)
- Build: Gradle Kotlin DSL
- Target: Windows first;

## 3) Architecture Principles

- Separate UI from logic.
- Make components for different logical parts
- Prefer:
    - `ui/` for composables + screens
    - `domain/` for pure logic (no UI, no filesystem, no Compose)
    - `data/` for filesystem access + image discovery + persistence + caching, etc.
    - `components/` for components for different logical parts of the UI
- Keep platform-specific code minimal and isolated.

Recommended module boundaries:

- `:app` (desktop)
- `commonMain` utility shared logic that can live on every app if it later needs to be multiplatform

## 4) Feature Rules (Slideshow)

- Must support:
    - drag & drop folder/images into window 
    - folder scan -> when we drag a folder, scan for all media supported types. No recursive scan.
    - next/prev/play/pause
    - speed: 0.5x / 1x / 2x
    - single image view + multi image view modes (later), grid, collage, etc.
- Must be responsive for large folders:
    - scanning happens off the UI thread. 
    - UI remains interactive
    - incremental loading is preferred

## 5) Coding Conventions

- Kotlin style: idiomatic Kotlin, immutable data where possible, smallest size where possible.
- Compose:
    - avoid heavy recompositions
    - use stable state holders
- Naming:
    - UI events: `onPlayClicked`, `onDropFiles`, etc.
    - state models: `SlideshowState`, `GalleryState`
- Avoid “clever” abstractions early; keep it readable.
- Avoid overcomplication and interfaces early

## 6) Testing Expectations

- Domain logic: unit tests required for non-trivial behavior.
- Data layer: tests for file discovery and filtering (use temp dirs).

## 7) Performance & Quality Bar

- Do not load full-resolution images into memory unnecessarily.
- Prefer thumbnails / scaled rendering when possible.
- Avoid blocking calls on the main thread.
- Handle 10k+ images gracefully (at least “doesn’t freeze”). At most 20 images, the next 20 will load after reaching a buffer using sliding window

## 8) When You (the agent) Make Changes

Always include:

- A short summary of what changed and why.
- Any follow-up tasks or risks.
- If you add a dependency, explain why and where it’s used.