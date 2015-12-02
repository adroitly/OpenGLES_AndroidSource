//
//  AppDelegate.m
//  OpenGL2.0Demo
//
//  Created by bn on 13-4-13.
//  Copyright (c) 2013年 百纳. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "AppDelegate.h"
#import "GLView.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    mWindow = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];  //初始化窗口
    mView = [[GLView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];      //初始化mView
    [mWindow addSubview: mView];                                                //mView加入窗口
    [mWindow makeKeyAndVisible];                                                //设置可触控
    
    return YES;
}

- (void) dealloc
{
    [mView release];//释放mView对象
    [mWindow release];//释放窗口对象
    [super dealloc];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
