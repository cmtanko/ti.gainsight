
# TiGainsight Module

## Overview

The TiGainsight module is a Titanium wrapper for GainsightPX, enabling tracking of user engagement, screen events, and user identification in your Titanium mobile app.

## Installation

1. Download or clone the TiGainsight module.
2. Add the module to your Titanium project.
3. Include the module in `tiapp.xml`:

```xml
<modules>
    <module platform="android">ti.gainsight</module>
</modules>
```

## Usage

### Importing the module

```javascript
var Gainsight = require('ti.gainsight');
```

### Initializing GainsightPX

```javascript
Gainsight.init('your-product-id', function(response) {
    console.log('Gainsight Initialized:', response);
});
```

### Enabling GainsightPX

```javascript
Gainsight.enable(function(response) {
    console.log('Gainsight Enabled:', response);
});
```

### Tracking Screen Events

```javascript
Gainsight.screenEvent('HomeScreen');
```

### Identifying Users and Accounts

```javascript
Gainsight.identify({
    userId: 'user123',
    email: 'user@example.com',
    firstName: 'John',
    lastName: 'Doe',
    countryCode: 'US',
    timeZone: 'PST',
    role: 'Admin',
    title: 'Software Engineer',
    accountId: 'account123',
    accountName: 'Acme Inc.'
}, function(response) {
    console.log('User Identified:', response);
});
```

### Simulating a Crash (for Testing)

```javascript
Gainsight.crashApp('native');
```

## License

This module is licensed under the MIT License.
