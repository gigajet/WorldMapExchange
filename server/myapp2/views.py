from django.shortcuts import render
from django.http import HttpResponse
from .forms import ImageForm
from django.http import JsonResponse
from django.core.files.storage import FileSystemStorage
from .digit import reg as hand_reg
from worldmap import settings as settingss
from django.views.decorators.csrf import csrf_exempt

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

@csrf_exempt
def image_upload_view(request):
    """Process images uploaded by users"""
    # if request.method == 'POST' and request.FILES['file']:
    if request.method == 'POST':
        
        #form = ImageForm(request.POST, request.FILES)
        myfile = bytes(request.body)

        f=open('test_cc.bmp','wb')
        f.write(myfile)
        f.close()
        f=open('test_cc.bmp','rb')

        fs = FileSystemStorage()
        #filename = fs.save(myfile.name,myfile)
        filename = fs.save('test_cc',f)
        path = './media/' + filename
        result = hand_reg.regconize(path)
        print('Result: ',result)
        print(len(result))
        return JsonResponse({'success':result})
    else:
        #form = ImageForm()
        return render(request, 'index.html')
