# DirtySU — KernelSU fork + BakaSU customization mix

## Base
- Upstream: `tiann/KernelSU` commit `08a3b087` (exact source of the working `KernelSU.apk` in Downloads, v3.3.0-52-g08a3b087).
- Fork: `Mafiadan6/DirtySU`, local checkout `/root/DirtySU` at that commit.
- Keep package `me.weishu.kernelsu` so it stays compatible with the root setup already working with DirtyMindz.
- No kernel/userspace/supercall changes. Manager-only mod.

## Features ported from BakaSU (donor source at /root/BakaSU-donor)
1. Custom background image — pick from gallery, persist to app-private storage, render behind the whole app.
2. Background controls — dim amount, blur, blur exponent, high-contrast mode, derive seed color from background.
3. Flash-card / module-card background toggle.
4. Keep upstream Material + Miuix theme modes, palette style, color spec, AMOLED — all still work.

## Our additions ("and more")
- Background fit mode (crop / fit / stretch) and independent opacity.
- One-tap "remove background" reset.
- All controls in one Appearance section, reachable from Settings.

## New concept UI (user directive, overrides plain reskin)
- New default UI mode **Hacker**: deep-black canvas, neon violet/cyan accents, mono HUD labels,
  faint grid + scanline texture, glowing translucent cards, large friendly tap targets.
- Custom image renders behind the Hacker shell; dim/blur/opacity/fit and image-derived accent work here.
- Reuse existing screen logic/navigation, but replace the visual shell: root HUD, bottom bar,
  top bars, cards, dialogs, status chips with DirtySU components.
- Legacy Material/Miuix remain available in the UI-style switcher but are no longer the face.

## Constraints from AGENTS.md
- Compose UI under `me.weishu.kernelsu.ui`, state in viewmodel, persistence in data layer.
- Reusable composables under `ui.component`.
- No hardcoded user-facing text — add to `values/strings.xml` (and zh if present).
- Run `spotlessCheck` (or `spotlessApply` then check) and `assembleRelease`.

## Build environment
- JDK 21 installed; SDK at `/opt/android-sdk` (platforms 34/36, build-tools 33/34/36).
- Upstream compileSdk/target likely 36 — verify, adjust only if required.
- ksud binary: extract `libksud.so` from the working KernelSU APK in Downloads into `manager/app/src/main/jniLibs/arm64-v8a/`.

## Release
- Tagged GitHub release on `Mafiadan6/DirtySU` with APK asset and SHA-256.
- No VPS IP anywhere in repo.
- GPL-3.0, fork attribution to tiann/KernelSU and Baka-SU/BakaSU.

## Status
- [x] correct base forked and checked out
- [x] donor features mapped
- [x] background engine ported (persistence, seed color, controls screen)
- [x] Hacker concept shell (palette/typography, backdrop grid/scanlines/glow, neon command bar, transparent scaffolds)
- [x] branding — app name DirtySU, version v4.3, dev name 爪卂丂ㄒ乇尺爪工刀ᗪ in About
- [x] build + checks green — assembleRelease BUILD SUCCESSFUL
- [x] APK metadata/source verify — package me.weishu.kernelsu, v4.3 / 32653, libksud byte-identical, no IP/branding
- [x] release published — tag v4.3, asset dirtysu-v4.3.apk, SHA-256 in notes
  - https://github.com/Mafiadan6/DirtySU/releases/tag/v4.3
  - SHA-256: 1c18fd8b6f29bf3df8d85304e1f9c754240d1ad0eceea2fa5ecfb112ff791f03
  - local source changes remain uncommitted/unpushed

## Root cause of "v4.3 not working with DirtyMindz" (diagnosed 11-19)
- DirtyMindz temp root = DirtyFrag exploit (CVE-2026-43284), loads KernelSU LKM, crowns manager by APK signature.
- Kernel accepts the DirtyMindz signature `6ba3c3e0…` (and/or official KernelSU `c371061b…`), NOT the Android debug key `982368f3…` used for v4.3.
- DirtyMindz signing key found: `/root/DirtyFrag/app/keystore.jks`, password `dirtyfrag`, cert `CN=DiryMindz`.
- Fix: re-sign DirtySU with the DirtyMindz keystore, rebuild, re-release (new tag/asset — do not overwrite v4.3).
- Supported KMIs and signature table documented in README.md.
- User direction: base stays KernelSU `me.weishu.kernelsu`, use the DirtyMindz signature from the manager.
