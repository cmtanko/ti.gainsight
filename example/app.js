// Example usage of the TiGainsight module
const Gainsight = require('ti.gainsight');

const win = Ti.UI.createWindow({
    backgroundColor: '#ffffff',
    layout: 'vertical'
});
const statusLabel = Ti.UI.createLabel({
    text: 'Tap buttons to test GainsightPX',
    textAlign: 'center',
    color: '#333',
    top: 50
});
const initButton = Ti.UI.createButton({
    title: 'Initialize GainsightPX',
    top: 100,
    width: 200
});
const enableButton = Ti.UI.createButton({
    title: 'Enable GainsightPX',
    top: 20,
    width: 200
});
const screenButton = Ti.UI.createButton({
    title: 'Track Screen Event',
    top: 20,
    width: 200
});
const identifyButton = Ti.UI.createButton({
    title: 'Identify User',
    top: 20,
    width: 200
});

initButton.addEventListener('click', function() {
    Gainsight.init('your-product-id', function(response) {
        Ti.API.info('Gainsight initialized:', response);
        statusLabel.text = 'GainsightPX initialized!';
    });
});

enableButton.addEventListener('click', function() {
    Gainsight.enable(function(response) {
        Ti.API.info('Gainsight enabled:', response);
        statusLabel.text = 'GainsightPX enabled!';
    });
});

screenButton.addEventListener('click', function() {
    Gainsight.screenEvent('ExampleScreen');
    Ti.API.info('Screen event tracked');
    statusLabel.text = 'Screen event tracked';
});

identifyButton.addEventListener('click', function() {
    Gainsight.identify({
        userId: 'user123',
        email: 'user@example.com',
        firstName: 'John',
        lastName: 'Doe',
        accountId: 'account123',
        accountName: 'Acme Inc.'
    }, function(response) {
        Ti.API.info('User identified:', response);
        statusLabel.text = 'User identified';
    });
});

win.add(statusLabel);
win.add(initButton);
win.add(enableButton);
win.add(screenButton);
win.add(identifyButton);
win.open();
