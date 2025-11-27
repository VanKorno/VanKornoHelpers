# LibClipboard

A lightweight clipboard helper for Android.

Features:

- Simple get / set / clear clipboard API  
- Global singleton  
- Auto-updating StateFlow with the current clipboard text  
- Optional listener callbacks  
- Optional system listener attachment (detect OS clipboard changes)  
- No Compose dependencies inside the library  

---

# Setup

## Initialize in your Activity

Attach/detach the system listener in your main or base activity.

```kotlin
override fun onStart() {
    super.onStart()
    LibClipboard.attachSystemListener(this)
}

override fun onStop() {
    LibClipboard.detachSystemListener(this)
    super.onStop()
}
```

This enables automatic tracking of clipboard changes from the OS.

---

# Basic API

## Read clipboard

```kotlin
val txt = LibClipboard.getClipboard(context)
```

## Write to clipboard

```kotlin
LibClipboard.setClipboard(context, "Hello")
```

## Clear clipboard

```kotlin
LibClipboard.clearClipboard(context)
```

---

# Observing Clipboard Changes

LibClipboard exposes a global, always-updated StateFlow:

```kotlin
val clipboardFlow: StateFlow<String>
```

---

# Usage Examples

## ViewModel (MVVM)

```kotlin
class MyVm : ViewModel() {

    val clipboardText = LibClipboard.clipboardFlow

    init {
        viewModelScope.launch {
            clipboardText.collect {
                // react to clipboard changes
            }
        }
    }

}
```

---

## Compose

```kotlin
@Composable
fun ClipboardWatcher() {
    val txt by LibClipboard.clipboardFlow.collectAsState()
    Text("Clipboard: $txt")
}
```

---

## Compose Paste Button Example

```kotlin
@Composable
fun PasteBtn(                                                        textInField: String
) {
    val clipboardText by LibClipboard.clipboardFlow.collectAsState()

    if (textInField.isEmpty()) {
        RoundBottomBtn(
            R.drawable.ic_paste,
            enabled = clipboardText.isNotEmpty(),
            onClick = {
                vm.ttsFromTxt.text = clipboardText
            }
        )
    }
}
```

---

## Listener Callbacks (non-Flow API)

```kotlin
val listener: (String) -> Unit = { txt ->
    println("Clipboard: $txt")
}

override fun onStart() {
    super.onStart()
    LibClipboard.addListener(listener)
}

override fun onStop() {
    LibClipboard.removeListener(listener)
    super.onStop()
}
```

---

## Background Worker Example

```kotlin
class BgWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        LibClipboard.clipboardFlow.collect { txt ->
            if (txt.contains("secret")) {
                // process something
            }
        }
        return Result.success()
    }

}
```

---

# API Summary
```text
getClipboard(context): String
setClipboard(context, txt: String)
clearClipboard(context)

attachSystemListener(context)
detachSystemListener(context)

clipboardFlow: StateFlow<String>
addListener(callback)
removeListener(callback)
```

---

# Notes

- System listener should be attached once (usually in your main Activity).  
- Flow and callbacks both receive notifications.  
- Has zero UI dependencies.  
- Works with Compose, ViewModels, Fragments, background workers, and services.