# Certificados Feature

## Overview
This feature allows authenticated users to generate certificates for their participation in research seedbeds (semilleros de investigación) through a modal dialog interface, similar to the reports feature.

## Files Created/Modified

### New Files:
- `src/components/certificates/CertificateCreationDialog.vue` - Modal dialog component for certificate generation

### Modified Files:
- `src/layouts/components/verticalNav.vue` - Added certificates modal trigger and dialog management
- `src/utils/api.ts` - Fixed GENERATE_CERTIFICATES endpoint path

### Removed Files (changed to modal approach):
- ~~`src/views/certificates/index.vue`~~ - Replaced with modal dialog
- ~~Route configurations~~ - No longer needed for modal approach

## How it Works

### Certificate Generation (Modal Approach):
1. User clicks "Certificados" in the navigation menu
2. A modal dialog opens with the certificate generation form
3. The dialog fetches:
   - User ID from auth store or `/users/me` endpoint
   - Research seedbeds assigned to the user via `RESEARCH_SEEDBEDS_BY_USER_ID/{userId}`
   - Available academic periods via `ACADEMIC_PERIODS`
4. User selects a seedbed and academic period
5. Clicking "Generar Certificado" calls `GENERATE_CERTIFICATES` endpoint with query parameters
6. The API response should contain download URL or file data for the certificate
7. Modal closes automatically after successful generation

### API Endpoints Used:
- `GET /users/me` - Get current user details
- `GET /research-seedbeds/seedbeds-by-user-id/{userId}` - Get user's seedbeds
- `GET /academic-periods/` - Get available academic periods
- `GET /users/student-seedbed-certificate?seedbedId=X&academicPeriodId=Y` - Generate certificate

### Features:
- ✅ User authentication check
- ✅ Dynamic loading of user's seedbeds
- ✅ Academic period selection
- ✅ Form validation
- ✅ Loading states
- ✅ Success/error messaging via toast notifications
- ✅ Modal dialog interface (consistent with reports feature)
- ✅ Navigation integration
- ✅ Responsive design with Vuetify components
- ✅ Automatic dialog close on success

### UI Components:
- **Modal Dialog**: Persistent, scrollable dialog with close button
- **Form Fields**: Select dropdowns for seedbed and academic period
- **Loading States**: Visual feedback during data fetching and generation
- **Action Buttons**: Cancel and Generate with proper validation
- **Toast Notifications**: Success/error feedback using the app's toast system

### Future Enhancements:
- Certificate history/listing modal
- Download functionality for previously generated certificates
- Certificate preview before generation
- Bulk certificate generation