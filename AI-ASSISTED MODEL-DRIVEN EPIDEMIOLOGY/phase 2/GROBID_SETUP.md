# GROBID Setup Guide

GROBID is used to parse scientific PDFs into structured sections (methods, results, references, etc.) so we can embed only the relevant parts for semantic search.

---

## Prerequisites

- Docker installed
- Python 3.x

### Install Docker (Arch Linux)

```bash
sudo pacman -S docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

Log out and back in after adding yourself to the docker group.

---

## Running GROBID

Run GROBID as a background service:

```bash
docker run -d --rm -p 8070:8070 -e JAVA_OPTS="-XX:-UseContainerSupport" --name grobid lfoppiano/grobid:0.8.0
```

The first run will pull the Docker image (~2-3GB), subsequent runs start immediately.

Wait about 60 seconds for models to load, then verify it's alive:

```bash
curl http://localhost:8070/api/isalive
# Should return: true
```

You can also open `http://localhost:8070` in your browser to use the web UI.

### Stop GROBID

```bash
docker stop grobid
```

---

## Python Client

```bash
pip install grobid-client-python
```

---

## Usage in Code

```python
import requests

def parse_pdf_sections(pdf_path: str) -> dict:
    """Parse a PDF and return structured sections."""
    with open(pdf_path, 'rb') as f:
        response = requests.post(
            'http://localhost:8070/api/processFulltextDocument',
            files={'input': f},
            data={'consolidateHeader': '0'}
        )
    return response.text  # Returns TEI XML

def extract_methods_and_results(tei_xml: str) -> str:
    """Extract only Methods and Results sections from TEI XML."""
    from bs4 import BeautifulSoup
    soup = BeautifulSoup(tei_xml, 'xml')
    
    relevant_sections = []
    for div in soup.find_all('div'):
        head = div.find('head')
        if head and any(kw in head.text.lower() for kw in ['method', 'result', 'model', 'abstract']):
            relevant_sections.append(div.get_text())
    
    return '\n\n'.join(relevant_sections)
```

---

## Notes

- GROBID must be running before calling the API
- Only Methods + Results sections are needed for parameter extraction — discard references, acknowledgements, funding, author contributions
- The `-XX:-UseContainerSupport` flag is required on newer Linux kernels
- Do NOT commit the Docker image to the repo — everyone pulls it themselves
