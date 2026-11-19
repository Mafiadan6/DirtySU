# DirtySU

A custom root manager built from the exact KernelSU manager that powers DirtyMindz, with
customization ideas borrowed from BakaSU and an original "Hacker" concept interface.

## Base and attribution

- **Upstream base:** [tiann/KernelSU](https://github.com/tiann/KernelSU) commit `08a3b087`
  (`v3.3.0-52-g08a3b087`) — the exact manager build used with DirtyMindz.
- **Customization donor:** [Baka-SU/BakaSU](https://github.com/Baka-SU/BakaSU) (GPL-3.0) —
  the custom-background idea and appearance controls were adapted from its source.

DirtySU is free software licensed under the **GNU General Public License v3.0**. The upstream
KernelSU and BakaSU copyright notices and license terms remain intact. This is a fork and
derivative work; full credit belongs to the original authors.

## What is different

- **Hacker concept UI (default):** deep-black canvas, neon violet/cyan accents, monospaced HUD
  labels, grid + scanline texture, glowing translucent cards, large tap targets.
- **Custom app background:** choose any image, tune darkness, opacity, blur, fit mode,
  high-contrast scrim, and derive the app accent colour from the image.
- **Same root engine:** package `me.weishu.kernelsu` and the original navigation/root flow are
  preserved, so manager↔kernel compatibility is unchanged.

## Development name

爪卂丂ㄒ乇尺爪工刀ᗪ

## DirtyMindz / DirtyFrag temp-root compatibility

DirtyMindz gets root through the DirtyFrag exploit (CVE-2026-43284), which loads a KernelSU LKM
on a locked bootloader. KernelSU crowns its manager by **APK signature** before granting root,
so a manager build must carry a signature the loaded kernel accepts.

Supported DirtyFrag KMIs (from the DirtyFrag source):

| KMI Version | Verified |
|---|---|
| android12-5.10 | Yes |
| android13-5.10 | Untested |
| android13-5.15 | Yes — verified on `SM-A176B` (Galaxy A17) |
| android14-5.15 | Untested |
| android14-6.1 | Not working (upstream mitigation) |
| android15-6.6 | Yes |
| android16-6.12 | Yes |
| android17-6.18 | Untested (module bundled) |

Latest verified-working KMI: `android16-6.12`. Latest KMI with a bundled module: `android17-6.18`.

### Signatures on record

| Build | Signature SHA-256 |
|---|---|
| KernelSU manager (Downloads) | `c371061b19d8c7d7d6133c6a9bafe198fa944e50c1b31c9d8daa8d7f1fc2d2d6` |
| DirtyMindz manager (`df.root`) | `6ba3c3e0531dc8dd524ed4b04e96ac3d41eb8da95623e3b60defb89c061192ac` |
| DirtySU v4.3 release (debug key) | `982368f314670544117ff73349fc596ed0af54f52289a0cc3dfa9b7bac255dc6` |

The DirtyMindz signing key is held in `/root/DirtyFrag/app/keystore.jks` (password `dirtyfrag`)
with cert `CN=DiryMindz, OU=Dev, O=DiryMindz`. The v4.3 release was signed with the Android debug
key, which the kernel does not accept — that is why it opens but gets no root. A rebuild must be
signed with the DirtyMindz signature `6ba3c3e0…` to be crowned by DirtyMindz's temp root.

## Building

```
cd manager
cp <ksud arm64 binary> app/src/main/jniLibs/arm64-v8a/libksud.so
./gradlew assembleRelease
```

The manager build requires `libksud.so` in `app/src/main/jniLibs/arm64-v8a/`.

## License

GPL-3.0. See [LICENSE](LICENSE).
