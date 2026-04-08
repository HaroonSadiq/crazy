# Echo & Spark - APK Build Automation Script
# Usage:
#   .\build-apk.ps1                                 # Build debug APK
#   .\build-apk.ps1 -BuildType release -Action run  # Build release, install & run

param(
    [string]$BuildType = "debug",  # "debug" or "release"
    [string]$Action = "build"       # "build", "install", or "run"
)

$projectDir = $PSScriptRoot
$apkDir = "$projectDir\app\build\outputs\apk\$BuildType"

Write-Host "`n🚀 Echo & Spark - APK Build Script" -ForegroundColor Cyan
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "Build Type: $BuildType" -ForegroundColor Yellow
Write-Host "Action: $Action" -ForegroundColor Yellow
Write-Host ""

# STEP 1: Validate prerequisites
Write-Host "✓ Checking prerequisites..." -ForegroundColor Green

# Check gradlew
if (-not (Test-Path "$projectDir\gradlew.bat")) {
    Write-Host "❌ gradlew.bat not found!" -ForegroundColor Red
    Write-Host "   Make sure you're running this from the EchoAndSpark project directory." -ForegroundColor Red
    exit 1
}

# Check Java
if ($null -eq $env:JAVA_HOME) {
    Write-Host "❌ JAVA_HOME environment variable not set!" -ForegroundColor Red
    Write-Host "`n📦 To fix:" -ForegroundColor Yellow
    Write-Host "   1. Download JDK 17: https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Cyan
    Write-Host "   2. Run in PowerShell (As Administrator):" -ForegroundColor Cyan
    Write-Host "      [Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-17.x.x', 'Machine')" -ForegroundColor Cyan
    Write-Host "   3. Restart PowerShell and try again" -ForegroundColor Cyan
    Write-Host ""
    exit 1
}

Write-Host "✓ Java: $env:JAVA_HOME" -ForegroundColor Green
Write-Host "✓ Project: $projectDir" -ForegroundColor Green
Write-Host ""

# STEP 2: Build APK
Write-Host "🏗️  Building $BuildType APK..." -ForegroundColor Cyan
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan

Set-Location $projectDir
$buildStart = Get-Date

if ($BuildType -eq "debug") {
    & ".\gradlew.bat" assembleDebug
} else {
    & ".\gradlew.bat" assembleRelease
}

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "❌ Build failed with exit code: $LASTEXITCODE" -ForegroundColor Red
    Write-Host ""
    Write-Host "Troubleshooting:" -ForegroundColor Yellow
    Write-Host "  • Check gradlew.bat is executable"
    Write-Host "  • Run: .\gradlew.bat clean assembleDebug"
    Write-Host "  • Check: java -version"
    Write-Host ""
    exit 1
}

$buildEnd = Get-Date
$buildTime = [math]::Round(($buildEnd - $buildStart).TotalSeconds, 1)

Write-Host ""
Write-Host "✓ Build completed in $buildTime seconds" -ForegroundColor Green
Write-Host ""

# STEP 3: Locate APK
$apkFile = "$apkDir\app-$BuildType.apk"
if (Test-Path $apkFile) {
    $apkSize = [math]::Round((Get-Item $apkFile).Length / 1MB, 2)
    Write-Host "✓ APK generated successfully" -ForegroundColor Green
    Write-Host "  File: app-$BuildType.apk" -ForegroundColor Cyan
    Write-Host "  Size: $apkSize MB" -ForegroundColor Cyan
    Write-Host "  Path: $apkFile" -ForegroundColor Cyan
} else {
    Write-Host "❌ APK not found at $apkDir" -ForegroundColor Red
    exit 1
}

Write-Host ""

# STEP 4: Process based on action
switch ($Action) {
    "build" {
        Write-Host "✅ APK Build Complete!" -ForegroundColor Green
        Write-Host ""
        Write-Host "Next steps:" -ForegroundColor Yellow
        Write-Host "  1. Start Android emulator (or connect device)"
        Write-Host "  2. Run: .\build-apk.ps1 -BuildType $BuildType -Action install" -ForegroundColor Cyan
        Write-Host "  3. Or run: .\build-apk.ps1 -BuildType $BuildType -Action run" -ForegroundColor Cyan
        break
    }
    
    "install" {
        Write-Host "📱 Installing APK on device..." -ForegroundColor Cyan
        Write-Host ""
        
        # Check for devices
        $adbOutput = & adb devices 2>$null
        $devices = @($adbOutput | Select-Object -Skip 1 | Where-Object { $_ -match "device`$" })
        
        if ($devices.Count -eq 0) {
            Write-Host "❌ No connected Android devices found!" -ForegroundColor Red
            Write-Host ""
            Write-Host "to fix:" -ForegroundColor Yellow
            Write-Host "  Android Emulator:" -ForegroundColor Cyan
            Write-Host "    • Open Android Studio"
            Write-Host "    • Tools → Device Manager → Create Device (or use existing)"
            Write-Host "    • Click play icon to start emulator" -ForegroundColor Cyan
            Write-Host ""
            Write-Host "  Physical Device:" -ForegroundColor Cyan
            Write-Host "    • Enable USB Debugging: Settings → Developer Options → USB Debugging" -ForegroundColor Cyan
            Write-Host "    • Connect via USB cable"
            Write-Host "    • Approve connection on device"
            Write-Host ""
            exit 1
        }
        
        Write-Host "Connected devices:" -ForegroundColor Green
        $devices | ForEach-Object { Write-Host "  • $_" }
        Write-Host ""
        
        Write-Host "Installing to device..." -ForegroundColor Cyan
        & adb install -r $apkFile
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "✅ Installation successful!" -ForegroundColor Green
        } else {
            Write-Host ""
            Write-Host "❌ Installation failed!" -ForegroundColor Red
            exit 1
        }
        break
    }
    
    "run" {
        Write-Host "📱 Installing and launching app..." -ForegroundColor Cyan
        Write-Host ""
        
        # Check for devices
        $adbOutput = & adb devices 2>$null
        $devices = @($adbOutput | Select-Object -Skip 1 | Where-Object { $_ -match "device`$" })
        
        if ($devices.Count -eq 0) {
            Write-Host "❌ No connected Android devices found!" -ForegroundColor Red
            exit 1
        }
        
        Write-Host "Connected devices:" -ForegroundColor Green
        $devices | ForEach-Object { Write-Host "  • $_" }
        Write-Host ""
        
        Write-Host "Installing APK..." -ForegroundColor Cyan
        & adb install -r $apkFile
        
        if ($LASTEXITCODE -ne 0) {
            Write-Host "❌ Installation failed!" -ForegroundColor Red
            exit 1
        }
        
        Write-Host "✓ Installed. Launching app..." -ForegroundColor Green
        & adb shell am start -n com.echospark/.MainActivity
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ App launched!" -ForegroundColor Green
            Write-Host ""
            Write-Host "View live logs:" -ForegroundColor Yellow
            Write-Host "  adb logcat | findstr 'EchoSpark'" -ForegroundColor Cyan
        } else {
            Write-Host "❌ Failed to launch app!" -ForegroundColor Red
            exit 1
        }
        break
    }
    
    default {
        Write-Host "❌ Unknown action: $Action" -ForegroundColor Red
        Write-Host "Valid actions: build, install, run" -ForegroundColor Yellow
        exit 1
    }
}

Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "Build Summary" -ForegroundColor Yellow
Write-Host "  Build Type: $BuildType"
Write-Host "  Build Time: $buildTime seconds"
Write-Host "  APK Size: $apkSize MB"
Write-Host "  Action: $Action"
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
