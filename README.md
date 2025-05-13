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

### Methods

```
1. init(productId: String, host: String, [callback: Function])
    Initializes the GainsightPX SDK.
    Parameters:
        productId (String) – Your Gainsight PX product ID.
        host (String) – PX host URL.
        callback (Function, optional) – Invoked with:

2. enable([callback: Function])
    Enables GainsightPX tracking and in-app engagements.
    Parameters:
        callback (Function, optional)

3. screen(screen: String)
    Tracks a screen view event in GainsightPX.
    Parameters:
        screen (String) – Name of the screen (e.g., "HomeScreen").

4. identify(userdata: Object, [callback: Function])
    Identifies a user and their associated account.
    Required userdata fields:
        userId
        accountId
        identifyId
        title (Account name)
        plan
        sfdcid (Salesforce contact ID)
        customSKUKey
        customSKUValue
        appversion
        userauthmethod
        userlicensetype
        userrole
```

### Importing the module

```javascript
var Gainsight = require('ti.gainsight')
```

### Initializing GainsightPX

```javascript
Gainsight.init('your-product-id', function (response) {
  console.log('Gainsight Initialized:', response)
})
```

### Enabling GainsightPX

```javascript
Gainsight.enable(function (response) {
  console.log('Gainsight Enabled:', response)
})
```

### Tracking Screen Events

```javascript
Gainsight.screenEvent('HomeScreen')
```

### Identifying Users and Accounts

```javascript
gainsight.identify(
  {
    userId: 'user123',
    accountId: 'acc456',
    identifyId: 'hash789',
    title: 'Acme Corp',
    plan: 'Premium',
    sfdcid: '003ABC',
    customSKUKey: 'sku',
    customSKUValue: 'Enterprise',
    appversion: '1.0.0',
    userauthmethod: 'OAuth',
    userlicensetype: 'Full',
    userrole: 'Admin'
  },
  function (e) {
    if (e.success) {
      console.log('User identified')
    } else {
      console.error(e.error)
    }
  }
)
```

### Simulating a Crash (for Testing)

```javascript
Gainsight.crashApp('native')
```

## License

This module is licensed under the MIT License.
