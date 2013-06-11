#!/usr/bin/env python
"""A simple script to display information about faces from the facetracer dataset.
This is useful for quickly seeing all the relevant data for a given face id.
It also shows how easy it is to parse the data for your own applications.

Note that fiducial point locations are RELATIVE TO THE CROP RECTANGLE.

You can use 'grep' on the output to just show particular fields.

Dataset webpage: http://www.cs.columbia.edu/CAVE/databases/facetracer/
"""


USAGE = '''FaceTracer dataset explorer, v1.0'
Usage: %s <face id>
'''

FIELDS = 'face_id crop_width crop_height crop_x0 crop_y0 yaw pitch roll left_eye_x0 left_eye_y0 left_eye_x1 left_eye_y1 right_eye_x0 right_eye_y0 right_eye_x1 right_eye_y1 mouth_x0 mouth_y0 mouth_x1 mouth_y1'.split()

def getLinesById(id, fname):
	"""Returns the lines split by '\t' where the first element is the given id"""
	lines = (l.strip().split('\t') for l in open(fname) if not l.startswith('#'))
	ret = [l for l in lines if int(l[0]) == int(id)]
	return ret

def fix(s):
	"""Fixes a string by replacing _ with spaces and putting it in title case"""
	return s.replace('_', ' ').title()

if __name__ == '__main__':
	import sys
	if len(sys.argv) < 2:
		print USAGE % (sys.argv[0])
		sys.exit()
	id = sys.argv[1]
	try:
		stats = getLinesById(id, 'facestats.txt')[0]
		for f, s in zip(FIELDS, stats):
			print '%s: %s' % (fix(f), s)
		urls = getLinesById(id, 'faceindex.txt')[0]
		imgurl, pageurl = urls[1:]
		print 'Image URL:', imgurl
		print 'Page URL:', pageurl
		attrs = getLinesById(id, 'facelabels.txt')
		for fid, attr, label in attrs:
			print '%s: %s' % (fix(attr), fix(label))
	except IndexError:
		print 'Error: "%s" is an invalid id!' % (id)
	except ValueError:
		print 'Error: "%s" is an invalid id!' % (id)

