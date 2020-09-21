from django.shortcuts import render
from django.http import HttpResponse
from .forms import ImageForm
from django.http import JsonResponse
from django.core.files.storage import FileSystemStorage
from .digit import reg as hand_reg
from worldmap import settings as settingss

# Create your views here.
def home(request):
    return HttpResponse('hello world')
    pass

def add(request):
    if request.method == 'GET':
        a = float(request.GET['a'])
        b = float(request.GET['b'])
        c = a+b
        return HttpResponse(str(c))
    else:
        return HttpResponse('method not supported')
    pass

def image_upload_view(request):
    """Process images uploaded by users"""
    if request.method == 'POST' and request.FILES['file']:
        #form = ImageForm(request.POST, request.FILES)
        myfile = request.FILES['file']
        fs = FileSystemStorage()
        filename = fs.save(myfile.name,myfile)
        path = './media/' + filename
        result = hand_reg.regconize(path)
        return JsonResponse({'success':result})
    else:
        #form = ImageForm()
        return render(request, 'index.html')