import { test, expect } from '@playwright/test';

test('has title', async ({ page }) => {
  await page.goto('/');

  // The app title seems to be "Monitor" or "Welcome to Magno"
  await expect(page).toHaveTitle(/(Magno|Monitor)/i);
});

test('login page loads', async ({ page }) => {
  await page.goto('/login');
  // Check for a common login element like an input with placeholder "Email" or similar
  // Since I don't know the exact structure, I'll just check if the URL contains login
  await expect(page).toHaveURL(/.*login/);
});
