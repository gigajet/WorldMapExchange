import cv2
import numpy as np
from keras.datasets import mnist
from keras.layers import Dense, Flatten
from keras.layers.convolutional import Conv2D
from keras.models import Sequential
from keras.utils import to_categorical
import matplotlib.pyplot as plt
from tensorflow import keras
import collections

def getx(c):
    return c[0]

def regconize(filename):
    para = 13
    res = ''
    freq = []
    while para < 250:
        res = clone(filename,para)
        para += 7
        if res != '' and res != '0':
            freq.append(res)
    counter = collections.Counter(freq)
    if counter:
        return counter.most_common(1)[0][0]
    else:
        return 'null'

def clone(filename, para):
    model = keras.models.load_model("./myapp2/digit/my_model2")

    image = cv2.imread(filename)
    print('Reg.py clone filename: ',filename)
    grey = cv2.cvtColor(image.copy(), cv2.COLOR_BGR2GRAY)
    ret, thresh = cv2.threshold(grey.copy(), para, 255, cv2.THRESH_BINARY_INV)
    contours, hierarchy = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    preprocessed_digits = []
    contours2 = []
    
    sh = image.shape[0]
    sw = image.shape[1]
    for c in contours:
        x,y,w,h = cv2.boundingRect(c)
        if w < sw/20 and h < sh/20:
            continue
        if h/w > 8:
            continue
        contours2.append((x,y,w,h))
    contours2.sort(key=getx)

    for c in contours2:
        (x,y,w,h) = c
        #if w < 40 and h < 40:
         #   continue
        
        # Creating a rectangle around the digit in the original image (for displaying the digits fetched via contours)
        cv2.rectangle(image, (x,y), (x+w, y+h), color=(0, 255, 0), thickness=2)
        
        # Cropping out the digit from the image corresponding to the current contours in the for loop
        digit = thresh[y:y+h, x:x+w]
        
        # Resizing that digit to (18, 18)
        resized_digit = cv2.resize(digit, (18,18))
        
        # Padding the digit with 5 pixels of black color (zeros) in each side to finally produce the image of (28, 28)
        padded_digit = np.pad(resized_digit, ((5,5),(5,5)), "constant", constant_values=0)
        
        # Adding the preprocessed digit to the list of preprocessed digits
        preprocessed_digits.append(padded_digit)
        
    inp = np.array(preprocessed_digits)

    result = ''

    for digit in preprocessed_digits:
        prediction = model.predict(digit.reshape(1, 28, 28, 1))
        result = result + str(np.argmax(prediction))
    return result
    
    # print ("\n\n---------------------------------------\n\n")
    # print ("=========PREDICTION============ \n\n")
    # plt.imshow(digit.reshape(28, 28), cmap="gray")
    # plt.show()
    # print("\n\nFinal Output: {}".format(np.argmax(prediction)))
    
    # print ("\nPrediction (Softmax) from the neural network:\n\n {}".format(prediction))
    
    # hard_maxed_prediction = np.zeros(prediction.shape)
    # hard_maxed_prediction[0][np.argmax(prediction)] = 1
    # print ("\n\nHard-maxed form of the prediction: \n\n {}".format(hard_maxed_prediction))
    # print ("\n\n---------------------------------------\n\n")