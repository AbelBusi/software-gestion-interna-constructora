#!/usr/bin/env python3
"""
Simple Python HTTP server for serving Spring Boot static resources
for testing the vanilla JS landing page without database dependencies.
"""

import http.server
import socketserver
import os
import mimetypes
from urllib.parse import urlparse, unquote

class SpringBootStaticHandler(http.server.SimpleHTTPRequestHandler):
    """Custom handler to serve Spring Boot static resources and templates"""
    
    def __init__(self, *args, **kwargs):
        # Set the base directory to the Spring Boot resources
        self.base_path = os.path.dirname(os.path.abspath(__file__))
        self.static_path = os.path.join(self.base_path, "src/main/resources/static")
        self.templates_path = os.path.join(self.base_path, "src/main/resources/templates")
        super().__init__(*args, **kwargs)
    
    def do_GET(self):
        """Handle GET requests"""
        parsed_path = urlparse(self.path)
        path = unquote(parsed_path.path)
        
        # Remove leading slash for easier path handling
        if path.startswith('/'):
            path = path[1:]
        
        # Default to index.html for root path
        if path == '' or path == '/':
            path = 'Landing Page/index.html'
            file_path = os.path.join(self.templates_path, path)
        # Handle static resources (CSS, JS, images)
        elif path.startswith('css/') or path.startswith('js/') or path.startswith('img/'):
            file_path = os.path.join(self.static_path, path)
        # Handle template files
        elif path.endswith('.html'):
            # Check if it's in Landing Page directory
            if '/' not in path:
                file_path = os.path.join(self.templates_path, 'Landing Page', path)
            else:
                file_path = os.path.join(self.templates_path, path)
        else:
            # Try static first, then templates
            file_path = os.path.join(self.static_path, path)
            if not os.path.exists(file_path):
                file_path = os.path.join(self.templates_path, path)
        
        # Normalize the path
        file_path = os.path.normpath(file_path)
        
        # Security check: ensure we're not serving files outside our directories
        if not (file_path.startswith(self.static_path) or file_path.startswith(self.templates_path)):
            self.send_error(403, "Access denied")
            return
        
        # Check if file exists
        if not os.path.exists(file_path) or not os.path.isfile(file_path):
            self.send_error(404, f"File not found: {path}")
            return
        
        # Get file content and MIME type
        try:
            with open(file_path, 'rb') as f:
                content = f.read()
            
            # Determine content type
            content_type, _ = mimetypes.guess_type(file_path)
            if content_type is None:
                if file_path.endswith('.html'):
                    content_type = 'text/html; charset=utf-8'
                elif file_path.endswith('.css'):
                    content_type = 'text/css; charset=utf-8'
                elif file_path.endswith('.js'):
                    content_type = 'application/javascript; charset=utf-8'
                else:
                    content_type = 'application/octet-stream'
            
            # Send response
            self.send_response(200)
            self.send_header('Content-Type', content_type)
            self.send_header('Content-Length', str(len(content)))
            self.send_header('Cache-Control', 'no-cache')
            self.end_headers()
            self.wfile.write(content)
            
            print(f"✓ Served: {path} -> {file_path}")
            
        except Exception as e:
            print(f"✗ Error serving {path}: {e}")
            self.send_error(500, f"Internal server error: {e}")
    
    def log_message(self, format, *args):
        """Override to customize logging"""
        print(f"[{self.date_time_string()}] {format % args}")

def run_server(port=8080):
    """Run the HTTP server"""
    
    # Change to the Spring Boot project directory
    project_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)))
    os.chdir(project_dir)
    
    print(f"🚀 Starting Python HTTP Server for CSI Contratistas Landing Page")
    print(f"📁 Project directory: {project_dir}")
    print(f"🌐 Server running at: http://localhost:{port}")
    print(f"📄 Landing page: http://localhost:{port}")
    print(f"🛑 Press Ctrl+C to stop the server\n")
    
    try:
        with socketserver.TCPServer(("", port), SpringBootStaticHandler) as httpd:
            httpd.serve_forever()
    except KeyboardInterrupt:
        print(f"\n🛑 Server stopped by user")
    except Exception as e:
        print(f"❌ Server error: {e}")

if __name__ == "__main__":
    run_server()
