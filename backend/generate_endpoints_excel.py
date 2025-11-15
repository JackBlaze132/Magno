import openpyxl
import re
import os

def extract_endpoints(file_path):
    endpoints = []
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Extract class-level @RequestMapping
    class_mapping_match = re.search(r'@RequestMapping\("([^"]*)"\)', content)
    base_path = class_mapping_match.group(1) if class_mapping_match else ""

    # Regex for method-level annotations and API-VERSION
    method_annotations = {
        'GET': r'@GetMapping\((?:path\s*=\s*)?"?([^")]*)"?,?\s*(?:headers\s*=\s*"API-VERSION=(\d)")?.*\)',
        'POST': r'@PostMapping\((?:path\s*=\s*)?"?([^")]*)"?,?\s*(?:headers\s*=\s*"API-VERSION=(\d)")?.*\)',
        'PUT': r'@PutMapping\((?:path\s*=\s*)?"?([^")]*)"?,?\s*(?:headers\s*=\s*"API-VERSION=(\d)")?.*\)',
        'DELETE': r'@DeleteMapping\((?:path\s*=\s*)?"?([^")]*)"?,?\s*(?:headers\s*=\s*"API-VERSION=(\d)")?.*\)'
    }

    for method, pattern in method_annotations.items():
        for match in re.finditer(pattern, content):
            path_part = match.group(1) if match.group(1) else ""
            api_version = match.group(2) if match.group(2) else "N/A" # Default to N/A if not found

            # Clean up path_part, remove leading/trailing slashes if present in both base and path_part to avoid double slashes
            if base_path.endswith('/') and path_part.startswith('/'):
                full_path = base_path + path_part[1:]
            else:
                full_path = base_path + path_part
            
            # Ensure path starts with a single slash
            if not full_path.startswith('/'):
                full_path = '/' + full_path
            
            # Remove trailing slash unless it's just "/"
            if full_path.endswith('/') and len(full_path) > 1:
                full_path = full_path[:-1]

            endpoints.append({
                'method': method,
                'path': full_path,
                'apiVersion': api_version,
                'sourceFile': os.path.basename(file_path)
            })
    return endpoints

def generate_excel(endpoints_data, output_file="endpoints.xlsx"):
    workbook = openpyxl.Workbook()
    sheet = workbook.active
    sheet.title = "API Endpoints"

    # Write headers
    headers = ["HTTP Method", "Endpoint", "API Version", "Source File"]
    sheet.append(headers)

    # Write data
    for endpoint in endpoints_data:
        sheet.append([endpoint['method'], endpoint['path'], endpoint['apiVersion'], endpoint['sourceFile']])

    workbook.save(output_file)
    print(f"Excel file '{output_file}' generated successfully.")

if __name__ == '__main__':
    controller_files = [
        "src/main/java/com/unibague/magno/infrastructure/input/rest/ResearchSeedbedStudentProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/ResearchSeedbedProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/StudentProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/ExternalUserProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/SecurityController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/UserRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/DependencyRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/InvestigationGroupProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/RoleRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/ResearchSeedbedRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/FunctionaryProfileRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/InvestigationGroupRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/EnumRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/AcademicPeriodRestController.java",
        "src/main/java/com/unibague/magno/infrastructure/input/rest/AcademicProgramRestController.java"
    ]

    all_endpoints = []
    for file in controller_files:
        all_endpoints.extend(extract_endpoints(file))

    generate_excel(all_endpoints)

