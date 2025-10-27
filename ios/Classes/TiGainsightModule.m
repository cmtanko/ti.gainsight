/**
 * ti.gainsight
 *
 * Created by Suchan Badyakar
 * Copyright (c) 2025 by Suchan Badyakar. All rights reserved.
 */

#import "TiGainsightModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"

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
  DebugLog(@"[INFO] TiGainsight module loaded");
}

#pragma mark Public APIs

// Note: iOS implementation for GainsightPX is not yet implemented
// This module currently supports Android only

@end
