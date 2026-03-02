from dotenv import load_dotenv

load_dotenv()
import os
from google import genai as _genai
import json
import sys

client = _genai.Client(api_key=os.getenv("GEMINI_API_KEY"))
model_name = "gemini-3-flash-preview"

response = client.models.generate_content(
    model=model_name,
    contents='Return JSON: {"test": "hello"}',
    config=None,
)

# Extract from parts like our code does
parts_text = []
if hasattr(response, "candidates") and response.candidates:
    content = getattr(response.candidates[0], "content", None)
    if content and hasattr(content, "parts"):
        for p in content.parts:
            if hasattr(p, "text") and p.text:
                parts_text.append(p.text)

result_text = "".join(parts_text).strip()
print("Result text:", repr(result_text[:200]))

# Now parse it - handle markdown
if "```json" in result_text:
    result_text = result_text.split("```json")[1].split("```")[0].strip()
    print("After split:", repr(result_text))
    parsed = json.loads(result_text)
    print("Parsed:", parsed)
