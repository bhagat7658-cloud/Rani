# Proud Rani — Gen-Z of JKL

This project is a native Android Java/XML-free UI implementation matching the supplied Proud Rani reference screens: splash, login, home, report incident, safe places, resources, community and profile.

## Build

- Android application module: `app`
- Application ID: `com.proudrani.app`
- Compile/target SDK: 35
- Minimum SDK: 24
- Release APK command on a standard Android CI runner: `gradle :app:assembleRelease`

The project intentionally uses standard Android APIs and no external runtime dependencies. The supplied Proud Rani artwork is included in `app/src/main/res/drawable/`.

## Safety behavior

The SOS action opens the device dialer for **112** rather than silently placing a call. The app should not be treated as a replacement for emergency services.

## Important

Real OTP login, Firebase backend, live maps, incident submission, push notifications and a moderated community backend are not connected in this UI build. They can be added after the visual build is accepted.
