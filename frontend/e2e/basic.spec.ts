import { test, expect } from '@playwright/test';

test.describe('Basic App Tests', () => {
  test('has title', async ({ page }) => {
    await page.goto('/');
    await expect(page).toHaveTitle(/(Magno|Monitor)/i);
  });

  test('unauthenticated user is redirected to login', async ({ page }) => {
    // When not authenticated, going to home should redirect to login
    await page.goto('/');
    
    // Wait for any redirects to complete
    await page.waitForLoadState('networkidle');
    
    // Should be on login page
    const currentUrl = page.url();
    expect(currentUrl).toContain('/login');
  });

  test('login page loads correctly', async ({ page }) => {
    await page.goto('/login');
    
    // Verify URL contains login
    await expect(page).toHaveURL(/.*login/);
    
    // Verify page has loaded with the login form
    await expect(page.locator('text=Ingresar').first()).toBeVisible();
  });

  test('login page has Google OAuth button', async ({ page }) => {
    await page.goto('/login');
    
    // Wait for the page to load
    await page.waitForLoadState('networkidle');
    
    // Look for the Google button - it's a VBtn with "google" text
    const googleButton = page.locator('button:has-text("google")');
    await expect(googleButton).toBeVisible();
    
    // Verify it has the Google logo image
    const googleImage = page.locator('img[src*="google"]');
    await expect(googleImage).toBeVisible();
  });

  test('login page has external user form', async ({ page }) => {
    await page.goto('/login');
    
    // Wait for page load
    await page.waitForLoadState('networkidle');
    
    // Check for external user section
    await expect(page.locator('text=Aliado Externo')).toBeVisible();
    
    // Check for email and password fields
    await expect(page.locator('input[type="email"]')).toBeVisible();
    await expect(page.locator('input[type="password"]')).toBeVisible();
    
    // Check for submit button
    await expect(page.locator('button:has-text("Ingresar")').last()).toBeVisible();
  });

  test('app has proper routing', async ({ page }) => {
    await page.goto('/login');
    await page.waitForLoadState('networkidle');
    await expect(page).toHaveURL(/.*login/);
    
    // When unauthenticated, trying to go to root redirects back to login
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    const url = page.url();
    expect(url).toContain('login');
  });
});

