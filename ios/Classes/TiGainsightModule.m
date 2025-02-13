/**
 * ti.gainsight
 *
 * Created by Your Name
 * Copyright (c) 2025 Your Company. All rights reserved.
 */

#import "TiGainsightModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import <PXKit/PXKit.h> 

@implementation TiGainsightModule

#pragma mark Internal

// This is generated for your module, please do not change it
- (id)moduleGUID
{
  return @"410cccc7-a735-4a95-8094-e4272fc2df8b";
}

// This is generated for your module, please do not change it
- (NSString *)moduleId
{
  return @"ti.gainsight";
}

#pragma mark Lifecycle

- (void)startup
{
  // This method is called when the module is first loaded
  // You *must* call the superclass
  [super startup];
  DebugLog(@"[DEBUG] %@ loaded", self);
}

#pragma Public APIs

- (NSString *)example:(id)args
{
  // Example method. 
  // Call with "MyModule.example(args)"
  return @"hello world";
}

- (NSString *)exampleProp
{
  // Example property getter. 
  // Call with "MyModule.exampleProp" or "MyModule.getExampleProp()"
  return @"Titanium rocks!";
}

- (void)setExampleProp:(id)value
{
  // Example property setter. 
  // Call with "MyModule.exampleProp = 'newValue'" or "MyModule.setExampleProp('newValue')"
}

@end
